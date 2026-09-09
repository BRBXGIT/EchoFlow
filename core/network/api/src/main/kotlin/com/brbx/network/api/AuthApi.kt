package com.brbx.network.api

import com.brbx.network.model.auth.AuthRequestDto
import com.brbx.network.model.auth.AuthResponseDto

interface AuthApi {
    suspend fun exchangeCode(authRequest: AuthRequestDto): AuthResponseDto

    suspend fun refreshTokens(refreshToken: String): AuthResponseDto
}