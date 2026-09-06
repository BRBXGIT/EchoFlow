package com.brbx.domain.use_case

import com.brbx.data.repository.AuthRepository
import com.brbx.domain.model.enums.UserAuthState
import kotlinx.coroutines.flow.Flow

internal class GetUserAuthStateUseCaseImpl(
    private val repository: AuthRepository,
) : GetUserAuthStateUseCase {
    override fun invoke(): Flow<UserAuthState> =
        repository.userAuthState
}
