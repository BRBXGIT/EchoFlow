package com.brbx.domain.use_case

import com.brbx.domain.model.common.Track

fun interface SetPlaybackQueueUseCase {
    operator fun invoke(tracks: List<Track>, startIndex: Int)
}
