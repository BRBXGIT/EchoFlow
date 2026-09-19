package com.brbx.data.controller

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.brbx.domain.model.PlayerState
import com.brbx.domain.model.common.Track
import com.brbx.domain.model.enums.PlaybackStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class AndroidPlayerController(
    private val player: Player,
    dispatcherMain: CoroutineDispatcher,
) : BasePlayerController() {

    private val scope = CoroutineScope(context = dispatcherMain + SupervisorJob())

    private var positionUpdateJob: Job? = null
    private var currentQueue = emptyList<Track>()

    private val playerListener = object : Player.Listener {
        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) =
            updateTrackAndPosition()

        override fun onIsPlayingChanged(isPlaying: Boolean) =
            handlePlayStateChange(isPlaying = isPlaying)

        override fun onPlaybackStateChanged(playbackState: Int) =
            updatePlaybackStatus()

        override fun onPositionDiscontinuity(
            oldPosition: Player.PositionInfo,
            newPosition: Player.PositionInfo,
            reason: Int,
        ) = updatePosition(positionMs = newPosition.positionMs)
    }

    init {
        player.addListener(playerListener)
        updatePlaybackStatus()
    }

    override fun pause() = player.pause()

    override fun resume() = player.play()

    override fun seekTo(positionMs: Long) = player.seekTo(positionMs)
        .also { updatePosition(positionMs) }

    override fun setQueue(tracks: List<Track>, startIndex: Int) = if (tracks.isEmpty()) {
        clearQueue()
    } else {
        initializeQueue(tracks = tracks, startIndex = startIndex)
    }

    override fun skipToNext() = player
        .takeIf { it.hasNextMediaItem() }
        ?.seekToNextMediaItem() ?: Unit

    override fun skipToPrevious() = when {
        player.currentPosition > SEEK_TO_PREVIOUS_THRESHOLD_MS -> player.seekTo(0L)
        player.hasPreviousMediaItem() -> player.seekToPreviousMediaItem()
        else -> player.seekTo(0L)
    }

    private fun clearQueue() {
        currentQueue = emptyList()
        player.clearMediaItems()
        reduce { PlayerState() }
        stopPositionUpdates()
    }

    private fun initializeQueue(tracks: List<Track>, startIndex: Int) {
        currentQueue = tracks
        val validIndex = startIndex.coerceIn(minimumValue = 0, maximumValue = tracks.lastIndex)
        val mediaItems = tracks.map { track -> track.mapToMediaItem() }

        player.setMediaItems(mediaItems, validIndex, 0L)
        player.prepare()
        player.play()

        reduce {
            copy(
                queue = tracks,
                currentTrack = tracks.getOrNull(index = validIndex),
                currentPositionMs = 0L,
                status = if (player.isPlaying) PlaybackStatus.Playing else PlaybackStatus.Stopped,
            )
        }
    }

    private fun updateTrackAndPosition() = reduce {
        copy(
            currentTrack = currentQueue.getOrNull(index = player.currentMediaItemIndex),
            currentPositionMs = player.currentPosition.coerceAt0(),
        )
    }

    private fun updatePosition(positionMs: Long) = reduce {
        copy(currentPositionMs = positionMs.coerceAt0())
    }

    private fun handlePlayStateChange(isPlaying: Boolean) {
        updatePlaybackStatus()
        if (isPlaying) startPositionUpdates() else stopPositionUpdates()
    }

    private fun updatePlaybackStatus() = reduce {
        copy(
            status = resolvePlaybackStatus(),
            currentPositionMs = player.currentPosition.coerceAt0(),
            currentTrack = currentQueue.getOrNull(index = player.currentMediaItemIndex) ?: currentTrack
        )
    }

    private fun resolvePlaybackStatus(): PlaybackStatus = when {
        player.isPlaying -> PlaybackStatus.Playing
        player.playbackState == Player.STATE_IDLE || player.playbackState == Player.STATE_ENDED -> PlaybackStatus.Idle
        else -> PlaybackStatus.Stopped
    }

    private fun startPositionUpdates() {
        stopPositionUpdates()
        positionUpdateJob = scope.launch {
            while (isActive) {
                updatePosition(positionMs = player.currentPosition)
                delay(timeMillis = POSITION_UPDATE_INTERVAL_MS)
            }
        }
    }

    private fun stopPositionUpdates() = positionUpdateJob?.cancel()
        .also { positionUpdateJob = null }

    private fun Long.coerceAt0() = this.coerceAtLeast(minimumValue = 0L)

    private fun Track.mapToMediaItem(): MediaItem {
        val metadata = MediaMetadata.Builder()
            .setTitle(title)
            .setArtist(user?.name ?: "unknown")
            .setArtworkUri(
                (highResArtworkUrl ?: artworkUrl)
                    ?.takeIf(predicate = { it.isNotBlank() })
                    ?.toUri()
            )
            .build()

        return MediaItem.Builder()
            .setMediaId(id.toString())
            .apply(block = {
                streamUrl
                    ?.takeIf(predicate = { it.isNotBlank() })
                    ?.let(block = { url -> setUri(url.toUri()) })
            })
            .setMediaMetadata(metadata)
            .setTag(this)
            .build()
    }

    companion object {
        private const val POSITION_UPDATE_INTERVAL_MS = 250L
        private const val SEEK_TO_PREVIOUS_THRESHOLD_MS = 5000L
    }
}

// Public cause needed in manifest
class PlaybackService : MediaSessionService(), KoinComponent {

    private val player by inject<Player>()
    private val intentProvider by inject<MediaServiceIntentProvider>()

    private var mediaSession: MediaSession? = null

    override fun onCreate() {
        super.onCreate()
        mediaSession = MediaSession.Builder(this, player)
            .setSessionActivity(intentProvider.provideSessionActivityIntent())
            .build()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? = mediaSession

    override fun onDestroy() {
        mediaSession?.run(block = {
            player.release()
            release()
            mediaSession = null
        })
        super.onDestroy()
    }
}

internal interface MediaServiceIntentProvider {
    fun provideSessionActivityIntent(): PendingIntent
}

internal class MediaServiceIntentProviderImpl(
    private val context: Context
) : MediaServiceIntentProvider {
    override fun provideSessionActivityIntent(): PendingIntent {
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
            ?: Intent(context, Class.forName("com.brbx.echoflow.MainActivity"))

        return PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        )
    }
}