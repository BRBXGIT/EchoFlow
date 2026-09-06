package com.brbx.domain.use_case

import com.brbx.data.builder.AuthUrlBuilder
import com.brbx.data.repository.AuthServerRepository

internal class JvmGetAuthUrlUseCaseImpl(
    private val builder: AuthUrlBuilder,
    private val serverRepository: AuthServerRepository,
) : GetAuthUrlUseCase {
    override fun invoke(): String {
        serverRepository.openServer()
        return builder.buildUrl()
    }
}
