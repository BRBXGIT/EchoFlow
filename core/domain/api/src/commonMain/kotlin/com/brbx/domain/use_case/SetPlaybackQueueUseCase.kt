package com.brbx.domain.use_case

import com.brbx.domain.model.common.Track
import com.brbx.domain.pagination.Paginator

interface SetPlaybackQueueUseCase {
    operator fun invoke(tracks: List<Track>, startIndex: Int)

    operator fun invoke(paginator: Paginator<Track>, startIndex: Int)
}
