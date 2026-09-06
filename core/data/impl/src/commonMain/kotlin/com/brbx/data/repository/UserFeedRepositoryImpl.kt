package com.brbx.data.repository

import androidx.paging.map
import com.brbx.data.handler.NetworkResponseHandler
import com.brbx.data.paging.TracksPagingSource
import com.brbx.data.paging.createPagingFlow
import com.brbx.domain.model.Track
import com.brbx.domain.model.User
import com.brbx.domain.model.`typealias`.TrackFlow
import com.brbx.domain.model.utils.RequestResult
import com.brbx.domain.model.utils.map
import com.brbx.network.api.UserFeedApi
import com.brbx.network.model.TrackDto
import com.brbx.network.model.UserDto
import kotlinx.coroutines.flow.map

internal class UserFeedRepositoryImpl(
    private val userFeedApi: UserFeedApi,
    private val responseHandler: NetworkResponseHandler,
) : UserFeedRepository {
    override fun getRecentlyPlayedTracks(): TrackFlow =
        createPagingFlow {
            TracksPagingSource(
                call = { next -> userFeedApi.getRecentlyPlayedTracks(url = next) },
                handler = responseHandler,
            )
        }.map { pagingData -> pagingData.map { dto -> dto.toDomain() } }

    override suspend fun getRecentlyPlayedTracksSnapshot(count: Int): RequestResult<List<Track>> =
        responseHandler.handle { userFeedApi.getRecentlyPlayedTracks() }
            .map { dto -> dto.collection.map { track -> track.toDomain() } }

    private fun TrackDto.toDomain() =
        Track(
            id = id,
            title = title,
            artworkUrl = artworkUrl,
            description = description,
            user = user?.toDomain(),
        )

    private fun UserDto.toDomain() =
        User(
            id = id,
            name = username,
            avatarUrl = avatarUrl,
        )
}