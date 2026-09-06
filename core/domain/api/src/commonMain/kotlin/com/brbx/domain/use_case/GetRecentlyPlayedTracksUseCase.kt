package com.brbx.domain.use_case

import com.brbx.domain.model.paging.TrackFlow

fun interface GetRecentlyPlayedTracksUseCase {
    suspend operator fun invoke(): TrackFlow
}
