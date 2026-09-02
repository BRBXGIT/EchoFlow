package com.brbx.domain.use_case

import com.brbx.data.builder.AuthLinkBuilder

internal class AndroidGetAuthLinkUseCaseImpl(
    private val builder: AuthLinkBuilder,
) : GetAuthLinkUseCase {
    override fun invoke(): String =
        builder.getLink()
}