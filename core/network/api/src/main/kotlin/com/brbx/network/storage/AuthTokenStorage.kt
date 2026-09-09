package com.brbx.network.storage

import com.brbx.network.model.auth.AuthTokensDto

interface AuthTokenStorage {
    suspend fun getTokens(): AuthTokensDto

    suspend fun setTokens(tokens: AuthTokensDto)

    suspend fun clearTokens()
}
