package com.brbx.domain.use_case

import com.brbx.data.builder.AuthLinkBuilder

internal class GetAuthLinkUseCaseImpl(
    private val authLinkBuilder: AuthLinkBuilder,
) : GetAuthLinkUseCase {
    override fun invoke(): String =
        authLinkBuilder.getLink()
}