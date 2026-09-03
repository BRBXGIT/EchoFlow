package com.brbx.domain.use_case

import kotlinx.coroutines.flow.StateFlow

fun interface GetCurrentAuthUrlUseCase {
    operator fun invoke(): StateFlow<String?>
}