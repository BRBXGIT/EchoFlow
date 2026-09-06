package com.brbx.domain.use_case

import com.brbx.domain.model.Track
import com.brbx.domain.model.utils.RequestResult

fun interface GetUserRecentlyPlayedSnapshotUseCase {
    suspend operator fun invoke(count: Int): RequestResult<List<Track>>
}