package com.brbx.data.interactor

import com.brbx.domain.model.AuthTokens
import kotlinx.coroutines.flow.Flow

interface UserAuthInteractor {
    val tokens: Flow<AuthTokens?>

    suspend fun saveTokens(tokens: AuthTokens)
}