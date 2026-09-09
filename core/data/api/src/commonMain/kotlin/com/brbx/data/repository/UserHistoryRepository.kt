package com.brbx.data.repository

import com.brbx.domain.model.base.ItemsCollection
import com.brbx.domain.model.common.RequestResult
import com.brbx.domain.model.common.Track
import kotlinx.coroutines.flow.StateFlow

interface UserHistoryRepository {
    val recentTracks: StateFlow<ItemsCollection<Track>?>

    suspend fun loadRecentTracks(): RequestResult<ItemsCollection<Track>>
}
