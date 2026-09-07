package com.brbx.data.repository

import com.brbx.data.common.toTrackFlow
import com.brbx.data.handler.NetworkResponseHandler
import com.brbx.data.paging.TracksPagingSource
import com.brbx.data.paging.createPagingFlow
import com.brbx.domain.model.`typealias`.TrackFlow
import com.brbx.network.api.RelatedApi

internal class RelatedRepositoryImpl(
    private val relatedApi: RelatedApi,
    private val responseHandler: NetworkResponseHandler,
) : RelatedRepository {
    override fun getSimilarTracks(id: Long): TrackFlow =
        createPagingFlow {
            TracksPagingSource(
                call = { next -> relatedApi.getSimilarTracks(id, url = next) },
                handler = responseHandler,
            )
        }.toTrackFlow()
}