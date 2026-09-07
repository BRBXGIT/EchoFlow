package com.brbx.home.model

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.brbx.domain.model.Track
import com.brbx.domain.model.`typealias`.TrackFlow
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.emptyFlow

@Immutable
internal data class HomeState(
    val feed: UiFeed = UiFeed(),
)

@Immutable
internal data class UiFeed(
    val recentPlayed: ImmutableList<Track> = persistentListOf(),
    val relatedToRecent: TrackFlow? = null,
)