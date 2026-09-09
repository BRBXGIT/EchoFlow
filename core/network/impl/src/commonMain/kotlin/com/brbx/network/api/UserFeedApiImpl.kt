package com.brbx.network.api

import com.brbx.network.client.ApiClientProvider
import com.brbx.network.model.base.CollectionDto
import com.brbx.network.model.common.TrackDto
import io.ktor.client.call.body
import io.ktor.client.request.get

internal class UserFeedApiImpl(
    private val clientProvider: ApiClientProvider,
) : UserFeedApi {
    override suspend fun getRecentlyPlayedTracks(): CollectionDto<TrackDto> =
        clientProvider.client.get(urlString = RecentlyPlayedEndPoint).body()

    private companion object {
        const val RecentlyPlayedEndPoint = "/me/recently-played/tracks"
    }
}