package com.brbx.network.api

import com.brbx.network.model.RecentlyPlayedResponseDto

interface UserFeedApi {
    suspend fun getRecentlyPlayedTracks(): RecentlyPlayedResponseDto
}