package com.brbx.network.api

import com.brbx.network.model.base.PaginatedDto
import com.brbx.network.model.common.TrackDto

interface UserFeedApi {
    suspend fun getFeedTracks(url: String?): PaginatedDto<TrackDto>
}