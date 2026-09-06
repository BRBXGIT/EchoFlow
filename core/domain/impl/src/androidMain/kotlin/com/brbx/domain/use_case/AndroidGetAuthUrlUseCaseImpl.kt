package com.brbx.domain.use_case

import com.brbx.data.builder.AuthUrlBuilder

internal class AndroidGetAuthUrlUseCaseImpl(
    private val builder: AuthUrlBuilder,
) : GetAuthUrlUseCase {
    override fun invoke(): String =
        builder.buildUrl()
}
