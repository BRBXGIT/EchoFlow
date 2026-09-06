package com.brbx.domain.use_case

import com.brbx.domain.model.paging.TrackFlow

fun interface GetPagedRelatedTracksUseCase {
    operator fun invoke(id: Int): TrackFlow
}