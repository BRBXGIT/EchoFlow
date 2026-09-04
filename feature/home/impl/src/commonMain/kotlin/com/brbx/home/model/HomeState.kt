package com.brbx.home.model

import androidx.compose.runtime.Immutable
import com.brbx.domain.model.Track
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
internal data class HomeState(
    val recentlyPlayed: ImmutableList<Track> = persistentListOf(),
)
