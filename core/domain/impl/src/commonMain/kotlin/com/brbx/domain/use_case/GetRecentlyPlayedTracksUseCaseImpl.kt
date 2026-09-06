package com.brbx.domain.use_case

import com.brbx.data.repository.UserFeedRepository
import com.brbx.domain.model.paging.TrackFlow

internal class GetRecentlyPlayedTracksUseCaseImpl(
    private val repository: UserFeedRepository,
) : GetRecentlyPlayedTracksUseCase {
    override suspend fun invoke(): TrackFlow =
        repository.getRecentlyPlayedTracks()
}
