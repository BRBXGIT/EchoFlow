package com.brbx.domain.use_case

fun interface SeekToPositionUseCase {
    operator fun invoke(positionMs: Long)
}
