package com.brbx.domain.use_case

import com.brbx.data.repository.UserLibraryRepository
import com.brbx.domain.model.RequestResult
import com.brbx.domain.model.Track

internal class GetUserRecentlyPlayedUseCaseImpl(
    private val repository: UserLibraryRepository
) : GetUserRecentlyPlayedUseCase {
    override suspend fun invoke(): RequestResult<List<Track>> =
        repository.getRecentlyPlayedTracks()
}