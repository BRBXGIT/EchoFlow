package com.brbx.domain.use_case

import com.brbx.data.repository.UserHistoryRepository
import com.brbx.domain.model.base.ItemsCollection
import com.brbx.domain.model.common.RequestResult
import com.brbx.domain.model.common.Track
import com.brbx.domain.model.common.failure
import com.brbx.domain.model.common.fold
import com.brbx.domain.model.common.success

internal class GetUserRecentlyTracksUseCaseImpl(
    private val historyRepository: UserHistoryRepository,
) : GetUserRecentlyTracksUseCase {
    override suspend fun invoke(): RequestResult<ItemsCollection<Track>> =
        historyRepository.loadRecentTracks()
            .fold(
                onException = { e -> failure(exception = e) },
                onSuccess = { tracks -> success(value = tracks) },
            )
}