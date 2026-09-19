package com.brbx.data.controller

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.time.Duration.Companion.milliseconds
import androidx.core.net.toUri

internal class AndroidPlayerController(
    private val player: Player,
    dispatcherMain: CoroutineDispatcher,
) : PlayerController {

    private val scope = CoroutineScope(context = dispatcherMain + SupervisorJob())
    private val _playerState = MutableStateFlow(value = PlayerState())
    private val playerListener = object : Player.Listener {
        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            val currentTrack = currentQueue.getOrNull(player.currentMediaItemIndex)
            _playerState.update { currentState ->
                currentState.copy(
                    currentTrack = currentTrack,
                    currentPositionMs = player.currentPosition.coerceAtLeast(minimumValue = 0L),
                )
            }
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            updatePlaybackStatus()
            if (isPlaying) startPositionUpdates() else stopPositionUpdates()
        }

        override fun onPlaybackStateChanged(playbackState: Int) = updatePlaybackStatus()

        override fun onPositionDiscontinuity(
            oldPosition: Player.PositionInfo,
            newPosition: Player.PositionInfo,
            reason: Int,
        ) =
            _playerState.update { currentState ->
                currentState.copy(
                    currentPositionMs = newPosition.positionMs.coerceAtLeast(minimumValue = 0L),
                )
            }
    }


    private var positionUpdateJob: Job? = null
    private var currentQueue = emptyList<Track>()

    override val playerState = _playerState.asStateFlow()

    init {
        player.addListener(playerListener)
        updatePlaybackStatus()
    }

    override fun pause() = player.pause()

    override fun resume() = player.play()

    override fun seekTo(positionMs: Long) {
        player.seekTo(positionMs)
        _playerState.update { currentState ->
            currentState.copy(currentPositionMs = positionMs.coerceAtLeast(minimumValue = 0L))
        }
    }

    override fun setQueue(tracks: List<Track>, startIndex: Int) {
        if (tracks.isEmpty()) {
            currentQueue = emptyList()
            player.clearMediaItems()
            _playerState.value = PlayerState()
            stopPositionUpdates()
            return
        }

        currentQueue = tracks
        val validIndex = startIndex.coerceIn(0, tracks.lastIndex)
        val mediaItems = tracks.map { it.toMediaItem() }

        player.setMediaItems(mediaItems, validIndex, 0L)
        player.prepare()
        player.play()

        val initialTrack = tracks.getOrNull(validIndex)
        _playerState.update { currentState ->
            currentState.copy(
                queue = tracks,
                currentTrack = initialTrack,
                currentPositionMs = 0L,
                status = if (player.isPlaying) PlaybackStatus.Playing else PlaybackStatus.Stopped,
            )
        }
    }

    override fun skipToNext() {
        if (player.hasNextMediaItem()) {
            player.seekToNextMediaItem()
        }
    }

    override fun skipToPrevious() {
        if (player.currentPosition > SEEK_TO_PREVIOUS_THRESHOLD_MS) {
            player.seekTo(0L)
        } else if (player.hasPreviousMediaItem()) {
            player.seekToPreviousMediaItem()
        } else {
            player.seekTo(0L)
        }
    }

    private fun updatePlaybackStatus() {
        val status = when {
            player.isPlaying -> PlaybackStatus.Playing
            player.playbackState == Player.STATE_IDLE || player.playbackState == Player.STATE_ENDED -> PlaybackStatus.Idle
            else -> PlaybackStatus.Stopped
        }
        _playerState.update { currentState ->
            currentState.copy(
                status = status,
                currentPositionMs = player.currentPosition.coerceAtLeast(minimumValue = 0L),
                currentTrack = currentQueue.getOrNull(player.currentMediaItemIndex) ?: currentState.currentTrack
            )
        }
    }

    private fun startPositionUpdates() {
        stopPositionUpdates()
        positionUpdateJob = scope.launch {
            while (isActive) {
                val currentPos = player.currentPosition.coerceAtLeast(0L)
                _playerState.update { currentState ->
                    currentState.copy(currentPositionMs = currentPos)
                }
                delay(timeMillis = POSITION_UPDATE_INTERVAL_MS)
            }
        }
    }

    private fun stopPositionUpdates() {
        positionUpdateJob?.cancel()
        positionUpdateJob = null
    }

    private fun Track.toMediaItem(): MediaItem {
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
                streamUrl?.takeIf { it.isNotBlank() }?.let { url ->
                    setUri(url.toUri())
                }
            }
            .setMediaMetadata(metadata)
            .setTag(this)
            .build()
    }

    companion object {
        private const val POSITION_UPDATE_INTERVAL_MS = 250L
        private const val SEEK_TO_PREVIOUS_THRESHOLD_MS = 5000L
    }
}

class PlaybackService : MediaSessionService(), KoinComponent {

    private val player by inject<Player>()

    private var mediaSession: MediaSession? = null

    override fun onCreate() {
        super.onCreate()
        val intent = packageManager.getLaunchIntentForPackage(packageName)
            ?: Intent(this, Class.forName("com.brbx.echoflow.MainActivity"))
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        )

        mediaSession = MediaSession.Builder(this, player)
            .setSessionActivity(pendingIntent)
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