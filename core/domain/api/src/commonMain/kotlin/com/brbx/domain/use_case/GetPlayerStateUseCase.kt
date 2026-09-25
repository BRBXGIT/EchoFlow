package com.brbx.domain.use_case

import com.brbx.domain.model.PlayerState
import kotlinx.coroutines.flow.StateFlow

fun interface GetPlayerStateUseCase {
    operator fun invoke(): StateFlow<PlayerState>
}
