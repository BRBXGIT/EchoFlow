package com.brbx.domain.use_case

import com.brbx.domain.model.common.Track

fun interface AddToPlaybackQueueUseCase {
    operator fun invoke(track: Track)
}