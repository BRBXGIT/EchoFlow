package com.brbx.domain.use_case

import com.brbx.data.repository.AuthServerRepository
import kotlinx.coroutines.flow.StateFlow

internal class GetCurrentAuthUrlUseCaseImpl(
    private val authServerRepository: AuthServerRepository,
) : GetCurrentAuthUrlUseCase {
    override fun invoke(): StateFlow<String?> =
        authServerRepository.currentUrl
}