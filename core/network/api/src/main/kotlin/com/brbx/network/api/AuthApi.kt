package com.brbx.network.api

import com.brbx.network.model.AuthRequestDto
import com.brbx.network.model.AuthResponseDto

interface AuthApi {
    suspend fun exchangeCode(authRequest: AuthRequestDto): AuthResponseDto

    suspend fun refreshTokens(refreshToken: String): AuthResponseDto
}