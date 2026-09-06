package com.brbx.data.repository

import com.brbx.data.handler.NetworkResponseHandler
import com.brbx.domain.model.RequestResult
import com.brbx.domain.model.Track
import com.brbx.domain.model.User
import com.brbx.network.api.UserFeedApi
import com.brbx.network.model.RecentlyPlayedResponseDto
import com.brbx.network.model.TrackDto
import com.brbx.network.model.UserDto

internal class UserFeedRepositoryImpl(
    private val userFeedApi: UserFeedApi,
    private val responseHandler: NetworkResponseHandler,
) : UserFeedRepository {
    override suspend fun getRecentlyPlayedTracks(): RequestResult<List<Track>> =
        responseHandler.handle { userFeedApi.getRecentlyPlayedTracks().toDomain() }

    private fun RecentlyPlayedResponseDto.toDomain() =
        collection.map { it.toDomain() }

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