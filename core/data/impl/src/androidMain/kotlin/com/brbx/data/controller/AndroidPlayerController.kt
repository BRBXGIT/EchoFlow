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
import com.brbx.domain.model.common.Track
import com.brbx.domain.model.enums.PlaybackStatus
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class AndroidPlayerController(
    private val player: Player,
    dispatcherMain: CoroutineDispatcher,
) : BasePlayerController(dispatcherMainImmediate = dispatcherMain) {

    private val playerListener = object : Player.Listener {
        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) =
            updateTrackAndPosition()

        override fun onIsPlayingChanged(isPlaying: Boolean) =
            handlePlayStateChange(
                isPlaying = isPlaying,
                status = resolvePlaybackStatus(),
            )

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
        .also { updatePosition(positionMs = positionMs) }

    override fun skipToNext() = player
        .takeIf { it.hasNextMediaItem() }
        ?.seekToNextMediaItem() ?: Unit

    override fun skipToPrevious() = when {
        player.currentPosition > SEEK_TO_PREVIOUS_THRESHOLD_MS -> player.seekTo(0L)
        player.hasPreviousMediaItem() -> player.seekToPreviousMediaItem()
        else -> player.seekTo(0L)
    }

    override fun clearQueue() {
        player.clearMediaItems()
        super.clearQueue()
    }

    override fun initializeQueue(tracks: List<Track>, startIndex: Int) {
        currentQueue = tracks
        currentTrackIndex = startIndex.coerceIn(minimumValue = 0, maximumValue = tracks.lastIndex)
        val mediaItems = tracks.map { track -> track.mapToMediaItem() }

        player.setMediaItems(mediaItems, currentTrackIndex, 0L)
        player.prepare()
        player.play()

        reduce {
            copy(
                queue = tracks,
                currentTrack = tracks.getOrNull(index = currentTrackIndex),
                currentPositionMs = 0L,
                status = if (player.isPlaying) PlaybackStatus.Playing else PlaybackStatus.Stopped,
            )
        }
    }

    override fun getCurrentPositionMs(): Long = player.currentPosition

    private fun updateTrackAndPosition() {
        currentTrackIndex = player.currentMediaItemIndex
        reduce {
            copy(
                currentTrack = currentQueue.getOrNull(index = currentTrackIndex),
                currentPositionMs = getCurrentPositionMs().coerceAt0(),
            )
        }
    }

    private fun updatePlaybackStatus() {
        currentTrackIndex = player.currentMediaItemIndex
        val isPlaying = player.isPlaying
        handlePlayStateChange(
            isPlaying = isPlaying,
            status = resolvePlaybackStatus(),
        )
    }

    private fun resolvePlaybackStatus(): PlaybackStatus = when {
        player.isPlaying -> PlaybackStatus.Playing
        (player.playbackState == Player.STATE_IDLE || player.playbackState == Player.STATE_ENDED) -> PlaybackStatus.Idle
        else -> PlaybackStatus.Stopped
    }

    private fun Track.mapToMediaItem(): MediaItem {
        val metadata = MediaMetadata.Builder()
            .setTitle(title)
            .setArtist(user?.name ?: "unknown")
            .setArtworkUri(
                (highResArtworkUrl ?: artworkUrl)
                    ?.takeIf { it.isNotBlank() }
                    ?.toUri()
            )
            .build()

        return MediaItem.Builder()
            .setMediaId(id.toString())
            .apply {
                streamUrl
                    ?.takeIf { it.isNotBlank() }
                    ?.let { url -> setUri(url.toUri()) }
            }
            .setMediaMetadata(metadata)
            .setTag(this)
            .build()
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
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }
}

internal interface MediaServiceIntentProvider {
    fun provideSessionActivityIntent(): PendingIntent
}

internal class MediaServiceIntentProviderImpl(
    private val context: Context,
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
