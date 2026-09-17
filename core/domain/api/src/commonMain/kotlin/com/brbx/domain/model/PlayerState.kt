package com.brbx.domain.model

import com.brbx.domain.model.common.Track
import com.brbx.domain.model.enums.PlaybackStatus

data class PlayerState(
    val currentTrack: Track? = null,
    val currentPositionMs: Long = 0L,
    val queue: List<Track> = emptyList(),
    val status: PlaybackStatus = PlaybackStatus.Idle,
)
