package com.brbx.data.repository

import com.brbx.data.common.toDomain
import com.brbx.data.handler.NetworkResponseHandler
import com.brbx.domain.model.`typealias`.TrackList
import com.brbx.domain.model.common.RequestResult
import com.brbx.network.api.UserFeedApi

internal class UserHistoryRepositoryImpl(
    private val userFeedApi: UserFeedApi,
    private val responseHandler: NetworkResponseHandler,
) : UserHistoryRepository {
    override suspend fun getRecentTracks(): RequestResult<TrackList> =
        responseHandler.handle {
            userFeedApi.getRecentlyPlayedTracks().collection.map { track -> track.toDomain() }
        }
}
