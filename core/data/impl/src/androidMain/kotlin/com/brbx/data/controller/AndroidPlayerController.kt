package com.brbx.data.controller

import androidx.media3.common.Player
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.brbx.domain.model.PlayerState
import com.brbx.domain.model.common.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class AndroidPlayerController(
    private val player: Player,
) : PlayerController {

    private val _playerState = MutableStateFlow(value = PlayerState())
    override val playerState = _playerState.asStateFlow()

    override fun pause() = player.pause()

    override fun resume() = player.play()

    override fun seekTo(positionMs: Long) = player.seekTo(positionMs)

    override fun setQueue(tracks: List<Track>, startIndex: Int) {
        TODO()
    }

    override fun skipToNext() {
        if (player.hasNextMediaItem()) player.seekToNextMediaItem()
    }

    override fun skipToPrevious() {
        if (player.hasPreviousMediaItem()) player.seekToPreviousMediaItem()
    }
}

class PlaybackService : MediaSessionService(), KoinComponent {

    private val player by inject<Player>()

    private var mediaSession: MediaSession? = null

    override fun onCreate() {
        super.onCreate()
        mediaSession = MediaSession.Builder(this, player).build()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo) = mediaSession

    override fun onDestroy() {
        player.release()
        mediaSession?.release()
        mediaSession = null
        super.onDestroy()
    }
}