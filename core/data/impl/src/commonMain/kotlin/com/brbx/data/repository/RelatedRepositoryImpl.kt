package com.brbx.data.repository

import com.brbx.data.common.toDomain
import com.brbx.data.handler.NetworkResponseHandler
import com.brbx.data.pagination.FlowPaginator
import com.brbx.domain.model.common.Track
import com.brbx.domain.pagination.Paginator
import com.brbx.network.api.RelatedApi

internal class RelatedRepositoryImpl(
    private val relatedApi: RelatedApi,
    private val handler: NetworkResponseHandler,
) : RelatedRepository {
    override fun getSimilarTracks(id: Long): Paginator<Track> =
        FlowPaginator(
            call = { next -> handler.handle { relatedApi.getSimilarTracks(id, url = next) } },
            mapper = { dto -> dto.toDomain() }
        )
}