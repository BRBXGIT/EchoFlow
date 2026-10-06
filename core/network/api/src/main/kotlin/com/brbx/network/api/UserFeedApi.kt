package com.brbx.network.api

import com.brbx.network.model.base.PaginatedDto
import com.brbx.network.model.common.TrackDto
import com.brbx.network.model.feed.FeedItemDto

interface UserFeedApi {
    suspend fun getFeedTracks(url: String?): PaginatedDto<FeedItemDto<TrackDto>>
}
