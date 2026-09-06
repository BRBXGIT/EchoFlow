package com.brbx.data.repository

import com.brbx.domain.model.Track
import com.brbx.domain.model.`typealias`.TrackFlow
import com.brbx.domain.model.utils.RequestResult

interface UserFeedRepository {
    fun getRecentlyPlayedTracks(): TrackFlow

    suspend fun getRecentlyPlayedTracksSnapshot(count: Int): RequestResult<List<Track>>
}