package com.brbx.network.inversion

import com.brbx.network.model.TokensDto

interface TokensInteractor {
    suspend fun getTokens(): TokensDto

    suspend fun setTokens(tokens: TokensDto)

    suspend fun clearTokens()
}