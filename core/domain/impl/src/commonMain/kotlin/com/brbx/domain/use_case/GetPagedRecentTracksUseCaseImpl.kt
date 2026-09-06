package com.brbx.domain.use_case

import com.brbx.data.repository.UserFeedRepository
import com.brbx.domain.model.paging.TrackFlow

internal class GetPagedRecentTracksUseCaseImpl(
    private val userFeedRepository: UserFeedRepository,
) : GetPagedRecentTracksUseCase {
    override fun invoke(): TrackFlow =
        userFeedRepository.getPagedRecentlyPlayedTracks()
}