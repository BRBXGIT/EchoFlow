package com.brbx.home.model

import androidx.compose.runtime.Immutable
import com.brbx.domain.model.common.Track
import com.brbx.domain.model.`typealias`.TrackFlow
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
internal data class HomeState(
    val feed: UiFeed = UiFeed(),
)

@Immutable
internal data class UiFeed(
    val recentPlayed: ImmutableList<Track> = persistentListOf(),
    val relatedToRecent: TrackFlow? = null,
)