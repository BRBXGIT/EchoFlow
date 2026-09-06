package com.brbx.data.repository

import com.brbx.domain.model.AuthTokens
import com.brbx.domain.model.utils.RequestResult
import com.brbx.domain.model.enums.UserAuthState
import kotlinx.coroutines.flow.Flow

interface UserAuthRepository {
    val userAuthState: Flow<UserAuthState>

    suspend fun authenticate(code: String, verifier: String): RequestResult<AuthTokens>
}