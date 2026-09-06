package com.brbx.network.api

import com.brbx.network.model.PaginatedTracksResponseDto

interface RelatedApi {
    suspend fun getSimilarTracks(
        id: Int,
        url: String?,
    ): PaginatedTracksResponseDto
}