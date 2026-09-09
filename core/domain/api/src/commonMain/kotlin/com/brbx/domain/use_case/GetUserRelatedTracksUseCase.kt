package com.brbx.domain.use_case

import com.brbx.domain.model.common.Track
import com.brbx.domain.pagination.Paginator

fun interface GetUserRelatedTracksUseCase {
    suspend operator fun invoke(): Paginator<Track>
}