package com.brbx.data.repository

import com.brbx.domain.model.AuthTokens
import com.brbx.domain.model.enums.UserAuthState
import com.brbx.domain.model.utils.RequestResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val userAuthState: Flow<UserAuthState>

    suspend fun authenticate(code: String, verifier: String): RequestResult<AuthTokens>
}
