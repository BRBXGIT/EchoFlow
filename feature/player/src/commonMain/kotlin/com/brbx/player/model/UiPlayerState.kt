package com.brbx.player.model

import androidx.compose.runtime.Immutable
import com.brbx.domain.model.PlayerState
import com.brbx.domain.model.common.Track
import com.brbx.domain.model.enums.PlaybackStatus
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Immutable
internal data class UiPlayerState(
    val currentTrack: Track? = null,
    val currentPositionMs: Long = 0L,
    val queue: ImmutableList<Track> = persistentListOf(),
    val status: PlaybackStatus = PlaybackStatus.Idle,
)

internal fun PlayerState.toUi() =
    UiPlayerState(
        currentTrack = currentTrack,
        currentPositionMs = currentPositionMs,
        queue = queue.toPersistentList(),
        status = status,
    )