package com.brbx.playlist

import androidx.compose.runtime.Immutable
import com.brbx.domain.model.common.Track
import com.brbx.domain.pagination.Paginator
import com.brbx.feature_common.utils.CommonText

@Immutable
sealed interface Playlist {
    @Immutable
    data class ReadyList(
        val title: CommonText,
        val tracks: List<Track>,
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