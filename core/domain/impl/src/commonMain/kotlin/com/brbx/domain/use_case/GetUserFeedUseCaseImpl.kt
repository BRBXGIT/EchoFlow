package com.brbx.domain.use_case

import com.brbx.data.repository.RelatedRepository
import com.brbx.data.repository.UserHistoryRepository
import com.brbx.domain.model.`typealias`.TrackFlow
import com.brbx.domain.model.common.RequestResult
import com.brbx.domain.model.common.failure
import com.brbx.domain.model.common.fold
import com.brbx.domain.model.common.success

internal class GetUserFeedUseCaseImpl(
    private val relatedRepository: RelatedRepository,
    private val historyRepository: UserHistoryRepository,
) : GetUserFeedUseCase {
    private var cachedRelated: TrackFlow? = null

    override suspend fun invoke(): RequestResult<UserFeed> =
        historyRepository.getRecentTracks().fold(
            onSuccess = { recent ->
                if (cachedRelated == null && recent.isNotEmpty()) {
                    cachedRelated = relatedRepository.getSimilarTracks(recent.first().id)
                }
                success(value = UserFeed(recentlyPlayed = recent, relatedToRecently = cachedRelated))
            },
            onException = ::failure
        )
}