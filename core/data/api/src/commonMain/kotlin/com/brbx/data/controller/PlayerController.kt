package com.brbx.data.controller

import com.brbx.domain.model.PlayerState
import com.brbx.domain.model.common.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface PlayerController {
    val playerState: StateFlow<PlayerState>

    fun pause()
    fun resume()

    fun setQueue(tracks: List<Track>, startIndex: Int)
    fun addToQueue(track: Track)

    fun seekTo(positionMs: Long)
    fun skipToNext()
    fun skipToPrevious()
}