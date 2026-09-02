package com.brbx.domain.use_case

import com.brbx.data.builder.AuthLinkBuilder
import com.brbx.data.repository.AuthServerRepository

internal class JvmGetAuthLinkUseCaseImpl(
    private val builder: AuthLinkBuilder,
    private val serverRepository: AuthServerRepository,
) : GetAuthLinkUseCase {
    override fun invoke(): String {
        serverRepository.openServer()
        return builder.getLink()
    }
}