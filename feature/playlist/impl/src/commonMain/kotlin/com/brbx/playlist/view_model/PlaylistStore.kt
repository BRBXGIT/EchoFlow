package com.brbx.playlist.view_model

import com.brbx.domain.model.common.Track
import com.brbx.domain.pagination.Paginator

internal sealed interface PlaylistType {
    @JvmInline value class Paged(val paginator: Paginator<Track>) : PlaylistType

    @JvmInline value class ReadyList(val tracks: List<Track>) : PlaylistType
}

internal class PlaylistStore {
    var playlist: PlaylistType? = null
}