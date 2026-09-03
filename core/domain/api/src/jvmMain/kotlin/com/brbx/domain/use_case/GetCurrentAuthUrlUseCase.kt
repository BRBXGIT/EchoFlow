package com.brbx.domain.use_case

fun interface GetCurrentAuthUrlUseCase {
    suspend operator fun invoke(): String
}