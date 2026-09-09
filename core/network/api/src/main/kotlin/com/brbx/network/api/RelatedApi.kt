package com.brbx.network.api

import com.brbx.network.model.base.PaginatedDto
import com.brbx.network.model.common.TrackDto

interface RelatedApi {
    suspend fun getSimilarTracks(id: Long, url: String?): PaginatedDto<TrackDto>
}