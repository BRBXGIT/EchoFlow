package com.brbx.network.api

import com.brbx.network.client.ApiClientProvider
import com.brbx.network.model.RecentlyPlayedResponseDto
import io.ktor.client.call.body
import io.ktor.client.request.get

internal class UserLibraryApiImpl(
    private val clientProvider: ApiClientProvider,
) : UserLibraryApi {
    override suspend fun getRecentlyPlayedTracks(): RecentlyPlayedResponseDto =
        clientProvider.client.get(urlString = RecentlyPlayedEndPoint).body()

    override suspend fun getFollowingFeed() {
        TODO("Not yet implemented")
    }

    override suspend fun getLikedTracks() {
        TODO("Not yet implemented")
    }

    override suspend fun getLikedPlaylists() {
        TODO("Not yet implemented")
    }

    private companion object {
        const val RecentlyPlayedEndPoint = "/me/recently-played/tracks"
    }
}