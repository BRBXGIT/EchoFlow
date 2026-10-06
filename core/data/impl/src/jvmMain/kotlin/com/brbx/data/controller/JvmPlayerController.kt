package com.brbx.data.controller

import com.brbx.domain.model.common.Track
import com.brbx.domain.model.enums.PlaybackStatus
import javafx.embed.swing.JFXPanel
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.util.Duration
import kotlinx.coroutines.CoroutineDispatcher

internal class JvmPlayerController(
    dispatcherMainImmediate: CoroutineDispatcher,
) : BasePlayerController(dispatcherMainImmediate) {

    private var mediaPlayer: MediaPlayer? = null

    init {
        runCatching { JFXPanel() }
    }

    // --- Playback Controls ---

    override fun pause() {
        mediaPlayer?.pause()
        updatePlaybackStatus()
    }

    override fun resume() {
        mediaPlayer?.play()
        updatePlaybackStatus()
    }

    override fun getCurrentPositionMs(): Long =
        mediaPlayer?.currentTime?.toMillis()?.toLong() ?: 0L

    override fun seekTo(positionMs: Long) {
        mediaPlayer?.seek(Duration.millis(positionMs.toDouble()))
        updatePosition(positionMs = positionMs)
    }

    override fun skipToNext() {
        val nextIndex = currentTrackIndex + 1
        if (nextIndex in currentQueue.indices) {
            playTrackAtIndex(nextIndex)
        }
    }

    override fun skipToPrevious() = when {
        getCurrentPositionMs() > SEEK_TO_PREVIOUS_THRESHOLD_MS -> seekTo(0L)
        currentTrackIndex - 1 in currentQueue.indices -> playTrackAtIndex(currentTrackIndex - 1)
        else -> seekTo(0L)
    }

    // --- Queue Controls ---

    override fun onTrackAddedNext(track: Track, insertIndex: Int) = Unit

    override fun clearQueue() {
        stopMediaPlayer()
        super.clearQueue()
    }

    override fun initializeQueue(tracks: List<Track>, startIndex: Int) {
        currentQueue = tracks
        playTrackAtIndex(startIndex.coerceIn(0, tracks.lastIndex))
    }

    // --- Private Helpers ---

    private fun playTrackAtIndex(index: Int) {
        stopMediaPlayer()

        currentTrackIndex = index
        val track = currentQueue.getOrNull(index) ?: return
        val url = track.streamUrl?.takeIf { it.isNotBlank() } ?: return

        runCatching {
            mediaPlayer = MediaPlayer(Media(url)).apply {
                setOnReady {
                    play()
                    handlePlayStateChange(isPlaying = true, status = PlaybackStatus.Playing)
                }
                setOnEndOfMedia { skipToNext() }
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
        handlePlayStateChange(
            isPlaying = status == PlaybackStatus.Playing,
            status = status,
        )
    }

    private fun resolvePlaybackStatus(): PlaybackStatus = when (mediaPlayer?.status) {
        MediaPlayer.Status.PLAYING -> PlaybackStatus.Playing
        MediaPlayer.Status.PAUSED, MediaPlayer.Status.STOPPED -> PlaybackStatus.Stopped
        else -> PlaybackStatus.Idle
    }
}
