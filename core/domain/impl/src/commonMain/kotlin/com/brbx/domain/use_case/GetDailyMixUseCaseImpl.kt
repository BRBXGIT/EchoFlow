package com.brbx.domain.use_case

import com.brbx.data.repository.UserFeedRepository
import com.brbx.domain.model.common.Track
import com.brbx.domain.pagination.Paginator

internal class GetDailyMixUseCaseImpl(
    private val feedRepository: UserFeedRepository,
) : GetDailyMixUseCase {
    private var cached: Paginator<Track>? = null

    override suspend fun invoke(): Paginator<Track> =
        cached ?: feedRepository.getFeedPaginator()
            .apply { loadNext() }
            .also { cached = it }
}