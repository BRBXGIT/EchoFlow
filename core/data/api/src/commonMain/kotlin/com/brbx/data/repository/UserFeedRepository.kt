package com.brbx.data.repository

import com.brbx.domain.model.RequestResult
import com.brbx.domain.model.Track

interface UserFeedRepository {
    suspend fun getRecentlyPlayedTracks(): RequestResult<List<Track>>
}