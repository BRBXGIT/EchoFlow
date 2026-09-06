package com.brbx.data.repository

import com.brbx.domain.model.Track
import com.brbx.domain.model.paging.TrackFlow
import com.brbx.domain.model.utils.RequestResult

interface UserFeedRepository {
    fun getPagedRecentlyPlayedTracks(): TrackFlow

    suspend fun getRecentlyPlayedTracks(count: Int): RequestResult<List<Track>>
}
