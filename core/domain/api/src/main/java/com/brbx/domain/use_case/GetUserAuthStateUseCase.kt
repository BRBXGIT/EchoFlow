package com.brbx.domain.use_case

import com.brbx.domain.model.UserAuthState
import kotlinx.coroutines.flow.Flow

fun interface GetUserAuthStateUseCase {
    operator fun invoke(): Flow<UserAuthState>
}