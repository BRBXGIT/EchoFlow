package com.brbx.playlist

import androidx.compose.runtime.Immutable
import com.brbx.design_system.components.components.TrackItem
import com.brbx.domain.model.common.Track
import com.brbx.domain.pagination.Paginator
import com.brbx.feature_common.utils.CommonText
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
sealed interface Playlist {
    @Immutable
    data class List(
        val title: CommonText,
        val tracks: ImmutableList<TrackItem>,
    ) : Playlist

    @Immutable
    data class Paged(
        val title: CommonText,
        val paginator: Paginator<Track>,
    ) : Playlist

    @Immutable
    @JvmInline
    value class Id(val id: Long) : Playlist
}