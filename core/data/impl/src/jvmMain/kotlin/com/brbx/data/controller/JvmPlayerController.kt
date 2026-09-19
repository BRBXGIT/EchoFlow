package com.brbx.data.controller

import com.brbx.domain.model.common.Track
import com.brbx.domain.model.enums.PlaybackStatus
import javafx.embed.swing.JFXPanel
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.util.Duration
import kotlinx.coroutines.CoroutineDispatcher

internal class JvmPlayerController(
    dispatcherMain: CoroutineDispatcher,
) : BasePlayerController(dispatcherMainImmediate = dispatcherMain) {

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

    override fun skipToNext() {
        val nextIndex = currentTrackIndex + 1
        if (nextIndex in currentQueue.indices) {
            playTrackAtIndex(index = nextIndex)
        }
    }

    override fun skipToPrevious() {
        val currentPositionMs = getCurrentPositionMs()
        when {
            currentPositionMs > SEEK_TO_PREVIOUS_THRESHOLD_MS -> seekTo(positionMs = 0L)
            (currentTrackIndex - 1) in currentQueue.indices -> playTrackAtIndex(index = currentTrackIndex - 1)
            else -> seekTo(positionMs = 0L)
        }
    }

    override fun clearQueue() {
        stopMediaPlayer()
        super.clearQueue()
    }

    override fun initializeQueue(tracks: List<Track>, startIndex: Int) {
        currentQueue = tracks
        val validIndex = startIndex.coerceIn(minimumValue = 0, maximumValue = tracks.lastIndex)
        playTrackAtIndex(index = validIndex)
    }

    override fun getCurrentPositionMs(): Long =
        mediaPlayer?.currentTime?.toMillis()?.toLong() ?: 0L

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
                    handlePlayStateChange(isPlaying = true, status = PlaybackStatus.Playing)
                }
                setOnEndOfMedia {
                    skipToNext()
                }
                setOnError {
                    handlePlayStateChange(isPlaying = false, status = PlaybackStatus.Stopped)
                }
            }
        }.onFailure {
            handlePlayStateChange(isPlaying = false, status = PlaybackStatus.Stopped)
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

    private fun updatePlaybackStatus() {
        val status = resolvePlaybackStatus()
        val isPlaying = status == PlaybackStatus.Playing
        handlePlayStateChange(
            isPlaying = isPlaying,
            status = status,
        )
    }

    private fun resolvePlaybackStatus(): PlaybackStatus = when (mediaPlayer?.status) {
        MediaPlayer.Status.PLAYING -> PlaybackStatus.Playing
        MediaPlayer.Status.PAUSED, MediaPlayer.Status.STOPPED -> PlaybackStatus.Stopped
        else -> PlaybackStatus.Idle
    }
}
