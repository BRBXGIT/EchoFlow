package com.brbx.domain.use_case

import com.brbx.domain.model.common.RequestResult

fun interface GetUserFeedUseCase {
    suspend operator fun invoke(): RequestResult<UserFeed>
}