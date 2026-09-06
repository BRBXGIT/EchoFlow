package com.brbx.data.repository

import com.brbx.data.common.toDomain
import com.brbx.data.common.toTrackFlow
import com.brbx.data.handler.NetworkResponseHandler
import com.brbx.data.paging.TracksPagingSource
import com.brbx.data.paging.createPagingFlow
import com.brbx.domain.model.Track
import com.brbx.domain.model.paging.TrackFlow
import com.brbx.domain.model.utils.RequestResult
import com.brbx.domain.model.utils.map
import com.brbx.network.api.UserFeedApi

internal class UserFeedRepositoryImpl(
    private val userFeedApi: UserFeedApi,
    private val responseHandler: NetworkResponseHandler,
) : UserFeedRepository {
    override fun getPagedRecentlyPlayedTracks(): TrackFlow =
        createPagingFlow {
            TracksPagingSource(
                call = { next -> userFeedApi.getRecentlyPlayedTracks(url = next) },
                handler = responseHandler,
            )
        }.toTrackFlow()

    override suspend fun getRecentlyPlayedTracks(count: Int): RequestResult<List<Track>> =
        responseHandler.handle { userFeedApi.getRecentlyPlayedTracks() }
            .map { dto -> dto.collection.map { track -> track.toDomain() } }
}
