package com.brbx.data.repository

import com.brbx.data.handler.NetworkResponseHandler
import com.brbx.domain.model.RequestResult
import com.brbx.domain.model.Track
import com.brbx.network.api.UserLibraryApi
import com.brbx.network.model.RecentlyPlayedResponseDto

internal class UserLibraryRepositoryImpl(
    private val userLibraryApi: UserLibraryApi,
    private val responseHandler: NetworkResponseHandler,
) : UserLibraryRepository {
    override suspend fun getRecentlyPlayedTracks(): RequestResult<List<Track>> =
        responseHandler.handle { userLibraryApi.getRecentlyPlayedTracks().toDomain() }

    private fun RecentlyPlayedResponseDto.toDomain() =
        collection.map { it.toDomain() }

    private fun RecentlyPlayedResponseDto.RecentlyPlayedItemDto.toDomain() =
        Track(
            id = track.id,
            title = track.title,
            artworkUrl = track.artworkUrl,
            streamable = track.streamable,
        )
}