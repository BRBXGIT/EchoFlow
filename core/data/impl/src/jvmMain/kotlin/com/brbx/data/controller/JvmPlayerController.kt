package com.brbx.data.controller

import com.brbx.domain.model.PlayerState
import com.brbx.domain.model.common.Track
import com.brbx.domain.model.enums.PlaybackStatus
import javafx.embed.swing.JFXPanel
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.util.Duration
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

internal class JvmPlayerController(
    dispatcherMain: CoroutineDispatcher,
) : BasePlayerController() {

    private val scope = CoroutineScope(context = dispatcherMain + SupervisorJob())

    private var positionUpdateJob: Job? = null
    private var currentQueue = emptyList<Track>()
    private var currentTrackIndex = -1
    private var mediaPlayer: MediaPlayer? = null

    init {
        runCatching { JFXPanel() }
    }

    override fun pause() {
        mediaPlayer?.pause()
        updatePlaybackStatus()
    }

    override fun resume() {
        mediaPlayer?.play()
        updatePlaybackStatus()
    }

    override fun seekTo(positionMs: Long) {
        mediaPlayer?.seek(Duration.millis(positionMs.toDouble()))
        updatePosition(positionMs = positionMs)
    }

    override fun setQueue(tracks: List<Track>, startIndex: Int) = if (tracks.isEmpty()) {
        clearQueue()
    } else {
        initializeQueue(tracks = tracks, startIndex = startIndex)
    }

    override fun skipToNext() {
        val nextIndex = currentTrackIndex + 1
        if (nextIndex in currentQueue.indices) {
            playTrackAtIndex(index = nextIndex)
        }
    }

    override fun skipToPrevious() {
        val currentPositionMs = mediaPlayer?.currentTime?.toMillis()?.toLong() ?: 0L
        when {
            currentPositionMs > SEEK_TO_PREVIOUS_THRESHOLD_MS -> seekTo(positionMs = 0L)
            (currentTrackIndex - 1) in currentQueue.indices -> playTrackAtIndex(index = currentTrackIndex - 1)
            else -> seekTo(positionMs = 0L)
        }
    }

    private fun clearQueue() {
        currentQueue = emptyList()
        currentTrackIndex = -1
        stopMediaPlayer()
        reduce { PlayerState() }
        stopPositionUpdates()
    }

    private fun initializeQueue(tracks: List<Track>, startIndex: Int) {
        currentQueue = tracks
        val validIndex = startIndex.coerceIn(minimumValue = 0, maximumValue = tracks.lastIndex)
        playTrackAtIndex(index = validIndex)
    }

    private fun playTrackAtIndex(index: Int) {
        stopMediaPlayer()

        currentTrackIndex = index
        val track = currentQueue.getOrNull(index = index) ?: return
        val url = track.streamUrl?.takeIf { it.isNotBlank() } ?: return

        runCatching {
            val media = Media(url)
            mediaPlayer = MediaPlayer(media).apply {
                setOnReady {
                    play()
                    handlePlayStateChange(isPlaying = true)
                }
                setOnEndOfMedia {
                    skipToNext()
                }
                setOnError {
                    handlePlayStateChange(isPlaying = false)
                }
            }
        }.onFailure {
            handlePlayStateChange(isPlaying = false)
        }

        reduce {
            copy(
                queue = currentQueue,
                currentTrack = track,
                currentPositionMs = 0L,
                status = PlaybackStatus.Idle,
            )
        }
    }

    private fun stopMediaPlayer() {
        mediaPlayer?.run {
            stop()
            dispose()
        }
        mediaPlayer = null
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
            currentPositionMs = getCurrentPositionMs().coerceAt0(),
            currentTrack = currentQueue.getOrNull(index = currentTrackIndex) ?: currentTrack,
        )
    }

    private fun resolvePlaybackStatus(): PlaybackStatus = when (mediaPlayer?.status) {
        MediaPlayer.Status.PLAYING -> PlaybackStatus.Playing
        MediaPlayer.Status.PAUSED, MediaPlayer.Status.STOPPED -> PlaybackStatus.Stopped
        else -> PlaybackStatus.Idle
    }

    private fun getCurrentPositionMs(): Long =
        mediaPlayer?.currentTime?.toMillis()?.toLong() ?: 0L

    private fun startPositionUpdates() {
        stopPositionUpdates()
        positionUpdateJob = scope.launch {
            while (isActive) {
                updatePosition(positionMs = getCurrentPositionMs())
                delay(timeMillis = POSITION_UPDATE_INTERVAL_MS)
            }
        }
    }

    private fun stopPositionUpdates() = positionUpdateJob?.cancel()
        .also { positionUpdateJob = null }

    private fun Long.coerceAt0() = this.coerceAtLeast(minimumValue = 0L)

    companion object {
        private const val POSITION_UPDATE_INTERVAL_MS = 250L
        private const val SEEK_TO_PREVIOUS_THRESHOLD_MS = 5000L
    }
}
