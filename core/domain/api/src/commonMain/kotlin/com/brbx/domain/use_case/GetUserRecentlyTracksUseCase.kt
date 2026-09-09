package com.brbx.domain.use_case

import com.brbx.domain.model.base.ItemsCollection
import com.brbx.domain.model.common.RequestResult
import com.brbx.domain.model.common.Track

fun interface GetUserRecentlyTracksUseCase {
    suspend operator fun invoke(): RequestResult<ItemsCollection<Track>>
}