package com.brbx.domain.use_case

import com.brbx.domain.model.paging.TrackFlow

fun interface GetPagedRecentTracksUseCase {
    operator fun invoke(): TrackFlow
}