package com.brbx.network.api

import com.brbx.network.client.ApiClientProvider
import com.brbx.network.model.RecentlyPlayedResponseDto
import io.ktor.client.call.body
import io.ktor.client.request.get

internal class UserFeedApiImpl(
    private val clientProvider: ApiClientProvider,
) : UserFeedApi {
    override suspend fun getRecentlyPlayedTracks(): RecentlyPlayedResponseDto =
        clientProvider.client.get(urlString = RecentlyPlayedEndPoint).body()

    private companion object {
        const val RecentlyPlayedEndPoint = "/me/recently-played/tracks"
    }
}