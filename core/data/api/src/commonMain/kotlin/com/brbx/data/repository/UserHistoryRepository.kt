package com.brbx.data.repository

import com.brbx.domain.model.common.RequestResult

interface UserHistoryRepository {
    suspend fun getRecentTracks(): RequestResult<TrackList>
}
