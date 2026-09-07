package com.brbx.data.repository

import com.brbx.domain.model.`typealias`.TrackList
import com.brbx.domain.model.util.RequestResult

interface UserHistoryRepository {
    suspend fun getRecentTracks(): RequestResult<TrackList>
}
