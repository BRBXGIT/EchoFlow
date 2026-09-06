package com.brbx.network.api

import com.brbx.network.model.PaginatedTracksResponseDto

interface UserFeedApi {
    suspend fun getRecentlyPlayedTracks(url: String? = null): PaginatedTracksResponseDto


}
