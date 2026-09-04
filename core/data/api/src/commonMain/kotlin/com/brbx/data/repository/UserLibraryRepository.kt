package com.brbx.data.repository

import com.brbx.domain.model.RequestResult
import com.brbx.domain.model.Track

interface UserLibraryRepository {
    suspend fun getRecentlyPlayedTracks(): RequestResult<List<Track>>
}