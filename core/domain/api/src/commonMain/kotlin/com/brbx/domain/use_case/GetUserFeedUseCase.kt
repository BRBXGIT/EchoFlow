package com.brbx.domain.use_case

import com.brbx.domain.model.UserFeed
import com.brbx.domain.model.util.RequestResult

fun interface GetUserFeedUseCase {
    suspend operator fun invoke(): RequestResult<UserFeed>
}