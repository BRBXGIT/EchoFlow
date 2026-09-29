package com.brbx.home.view_model

import com.brbx.domain.model.common.Track
import com.brbx.domain.pagination.Paginator

internal class HomeTracksStore {

    private val tracksMap = mutableMapOf<Long, Track>()

    var recent: List<Track> = emptyList()
        set(value) {
            field = value
            putTracks(value)
        }

    var relatedToRecent: Paginator<Track>? = null
        set(value) {
            field = value
            putTracks(value?.state?.value?.items)
        }

    private fun putTracks(tracks: Iterable<Track>?) =
        tracks?.forEach { track -> tracksMap[track.id] = track }
}