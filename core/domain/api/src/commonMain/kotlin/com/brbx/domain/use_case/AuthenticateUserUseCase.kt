package com.brbx.domain.use_case

import com.brbx.domain.model.common.RequestResult

fun interface AuthenticateUserUseCase {
    suspend operator fun invoke(rawUri: String): RequestResult<Unit>
}