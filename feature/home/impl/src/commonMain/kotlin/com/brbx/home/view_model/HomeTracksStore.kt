package com.brbx.home.view_model

import com.brbx.domain.model.common.Track

internal class HomeTracksStore {

    private val tracksMap = mutableMapOf<Long, Track>()

    var recent: List<Track> = emptyList()
        set(value) {
            field = value
            putTracks(value)
        }

    var relatedToRecent: List<Track> = emptyList()
        set(value) {
            field = value
            putTracks(value)
        }

    fun findTrack(id: Long): Track? = tracksMap[id]

    private fun putTracks(tracks: Iterable<Track>) =
        tracks.forEach { track -> tracksMap[track.id] = track }
}