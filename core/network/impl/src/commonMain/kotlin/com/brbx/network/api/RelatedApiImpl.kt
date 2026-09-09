package com.brbx.network.api

import com.brbx.network.client.ApiClientProvider
import com.brbx.network.model.PaginatedTracksResponseDto
import com.brbx.network.utils.setupPaging
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments

internal class RelatedApiImpl(
    private val clientProvider: ApiClientProvider,
) : RelatedApi {
    override suspend fun getSimilarTracks(id: Long, url: String?): PaginatedTracksResponseDto =
        clientProvider.client.get(urlString = url ?: "tracks/$id/related") {
            if (url == null) setupPaging()
        }.body()
}