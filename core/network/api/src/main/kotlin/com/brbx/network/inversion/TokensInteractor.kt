package com.brbx.network.inversion

import com.brbx.network.model.Tokens

interface TokensInteractor {
    suspend fun getTokens(): Tokens

    suspend fun setTokens(tokens: Tokens)

    suspend fun clearTokens()
}