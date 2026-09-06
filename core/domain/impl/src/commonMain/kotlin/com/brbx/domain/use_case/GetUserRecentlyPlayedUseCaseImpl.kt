package com.brbx.domain.use_case

import com.brbx.data.repository.UserFeedRepository
import com.brbx.domain.model.`typealias`.TrackFlow

internal class GetUserRecentlyPlayedUseCaseImpl(
    private val repository: UserFeedRepository
) : GetUserRecentlyPlayedUseCase {
    override suspend fun invoke(): TrackFlow =
        repository.getRecentlyPlayedTracks()
}