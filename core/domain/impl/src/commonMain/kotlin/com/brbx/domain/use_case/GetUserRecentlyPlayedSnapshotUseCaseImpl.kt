package com.brbx.domain.use_case

import com.brbx.data.repository.UserFeedRepository
import com.brbx.domain.model.Track
import com.brbx.domain.model.utils.RequestResult

internal class GetUserRecentlyPlayedSnapshotUseCaseImpl(
    private val feedRepository: UserFeedRepository,
) : GetUserRecentlyPlayedSnapshotUseCase {
    override suspend fun invoke(count: Int): RequestResult<List<Track>> =
        feedRepository.getRecentlyPlayedTracksSnapshot(count)
}