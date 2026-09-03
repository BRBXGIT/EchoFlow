package com.brbx.domain.use_case

import com.brbx.data.repository.AuthServerRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first

internal class GetCurrentAuthUrlUseCaseImpl(
    private val authServerRepository: AuthServerRepository,
) : GetCurrentAuthUrlUseCase {
    override suspend fun invoke(): String = authServerRepository.currentUrl
        .filterNotNull()
        .first()
        .also { authServerRepository.clearUrl() }
}