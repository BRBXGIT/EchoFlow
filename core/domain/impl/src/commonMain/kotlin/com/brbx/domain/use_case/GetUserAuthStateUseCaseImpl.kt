package com.brbx.domain.use_case

import com.brbx.data.repository.UserAuthRepository
import com.brbx.domain.model.UserAuthState
import kotlinx.coroutines.flow.Flow

internal class GetUserAuthStateUseCaseImpl(
    private val repository: UserAuthRepository,
) : GetUserAuthStateUseCase {
    override fun invoke(): Flow<UserAuthState> =
        repository.userAuthState
}