package com.brbx.data.repository

import com.brbx.data.common.toDomain
import com.brbx.data.handler.NetworkResponseHandler
import com.brbx.domain.model.base.ItemsCollection
import com.brbx.domain.model.common.RequestResult
import com.brbx.domain.model.common.Track
import com.brbx.domain.model.common.failure
import com.brbx.domain.model.common.fold
import com.brbx.domain.model.common.success
import com.brbx.network.api.UserHistoryApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class UserHistoryRepositoryImpl(
    private val userHistoryApi: UserHistoryApi,
    private val handler: NetworkResponseHandler,
) : UserHistoryRepository {

    private val _recentTracks = MutableStateFlow<ItemsCollection<Track>?>(value = ItemsCollection())
    override val recentTracks = _recentTracks.asStateFlow()

    override suspend fun loadRecentTracks(): RequestResult<ItemsCollection<Track>> =
        handler.handle { userHistoryApi.getRecentlyPlayedTracks() }
            .fold(
                onException = { e -> failure(exception = e) },
                onSuccess = { dto ->
                    val mapped = dto.toDomain { trackDto -> trackDto.toDomain() }
                    _recentTracks.value = mapped
                    success(value = mapped)
                },
            )
}
