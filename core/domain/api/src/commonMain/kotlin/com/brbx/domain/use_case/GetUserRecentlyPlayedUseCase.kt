package com.brbx.domain.use_case

import com.brbx.domain.model.RequestResult
import com.brbx.domain.model.Track

fun interface GetUserRecentlyPlayedUseCase {
    suspend operator fun invoke(): RequestResult<List<Track>>
}