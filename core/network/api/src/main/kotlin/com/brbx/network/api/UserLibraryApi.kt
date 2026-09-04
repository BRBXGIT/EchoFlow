package com.brbx.network.api

import com.brbx.network.model.RecentlyPlayedResponseDto

interface UserLibraryApi {
    suspend fun getRecentlyPlayedTracks(): RecentlyPlayedResponseDto

    suspend fun getFollowingFeed()

    suspend fun getLikedTracks()

    suspend fun getLikedPlaylists()
}