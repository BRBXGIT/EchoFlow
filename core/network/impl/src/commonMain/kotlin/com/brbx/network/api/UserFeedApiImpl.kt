package com.brbx.network.api

import com.brbx.network.client.ApiClientProvider
import com.brbx.network.model.base.PaginatedDto
import com.brbx.network.model.common.TrackDto
import com.brbx.network.model.feed.FeedItemDto
import com.brbx.network.utils.setupPaging
import io.ktor.client.call.body
import io.ktor.client.request.get

internal class UserFeedApiImpl(
    private val clientProvider: ApiClientProvider,
) : UserFeedApi {
    override suspend fun getFeedTracks(url: String?): PaginatedDto<FeedItemDto<TrackDto>> =
        clientProvider.client.get(urlString = url ?: FeedTracksEndPoint) { setupPaging() }.body()

    private companion object {
        const val FeedTracksEndPoint = "/me/feed/tracks"
    }
}
