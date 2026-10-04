package com.brbx.player.model

import androidx.compose.runtime.Immutable
import com.brbx.domain.model.common.Track
import com.brbx.domain.model.enums.PlaybackStatus
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class UiPlayerState(
    val currentTrack: Track? = null,
    val currentPositionMs: Long = 0L,
    val queue: ImmutableList<Track> = persistentListOf(),
    val status: PlaybackStatus = PlaybackStatus.Idle,
)