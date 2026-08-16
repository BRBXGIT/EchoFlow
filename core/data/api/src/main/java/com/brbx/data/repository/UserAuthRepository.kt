package com.brbx.data.repository

import com.brbx.domain.model.UserAuthState
import kotlinx.coroutines.flow.Flow

interface UserAuthRepository {
    val userAuthState: Flow<UserAuthState>
}