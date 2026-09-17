package com.brbx.data.controller

import com.brbx.domain.model.PlayerState
import com.brbx.domain.model.common.Track
import kotlinx.coroutines.flow.StateFlow

interface PlayerController {
    val playerState: StateFlow<PlayerState>

    fun pause()
    fun resume()

    fun seekTo(positionMs: Long)
    fun setQueue(tracks: List<Track>, startIndex: Int)

    fun skipToNext()
    fun skipToPrevious()
}