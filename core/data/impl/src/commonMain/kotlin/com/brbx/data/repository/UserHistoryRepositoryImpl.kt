package com.brbx.data.repository

import com.brbx.data.common.toDomain
import com.brbx.data.handler.NetworkResponseHandler
import com.brbx.domain.model.base.ItemsCollection
import com.brbx.domain.model.common.RequestResult
import com.brbx.domain.model.common.Track
import com.brbx.domain.model.common.failure
import com.brbx.domain.model.common.fold
import com.brbx.domain.model.common.success
import com.brbx.network.api.UserFeedApi
import com.brbx.network.model.base.CollectionDto
import com.brbx.network.model.common.TrackDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class UserHistoryRepositoryImpl(
    private val userFeedApi: UserFeedApi,
    private val handler: NetworkResponseHandler,
) : UserHistoryRepository {

    private val _recentTracks = MutableStateFlow(value = ItemsCollection<Track>())
    override val recentTracks = _recentTracks.asStateFlow()

    override suspend fun loadRecentTracks(): RequestResult<Unit> =
        handler.handle { userFeedApi.getRecentlyPlayedTracks() }
            .fold(
                onSuccess = { dto ->
                    _recentTracks.value = dto.toDomain()
                    success(value = Unit)
                },
                onException = { e -> failure(exception = e) }
            )

    private fun CollectionDto<TrackDto>.toDomain() =
        ItemsCollection(collection.map { it.toDomain() })
}
