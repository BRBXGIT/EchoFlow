package com.brbx.data.datasource

import com.brbx.domain.model.auth.AuthTokens
import kotlinx.coroutines.flow.Flow

interface AuthTokenDataSource {
    val tokens: Flow<AuthTokens?>

    suspend fun saveTokens(tokens: AuthTokens)

    suspend fun clearTokens()
}
