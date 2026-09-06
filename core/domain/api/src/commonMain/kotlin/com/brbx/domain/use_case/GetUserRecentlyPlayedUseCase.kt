package com.brbx.domain.use_case

import com.brbx.domain.model.`typealias`.TrackFlow

fun interface GetUserRecentlyPlayedUseCase {
    suspend operator fun invoke(): TrackFlow
}