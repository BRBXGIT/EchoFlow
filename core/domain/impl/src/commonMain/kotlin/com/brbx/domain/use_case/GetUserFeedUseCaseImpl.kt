package com.brbx.domain.use_case

import com.brbx.data.repository.RelatedRepository
import com.brbx.data.repository.UserHistoryRepository
import com.brbx.domain.model.UserFeed
import com.brbx.domain.model.`typealias`.TrackFlow
import com.brbx.domain.model.util.RequestResult
import com.brbx.domain.model.util.failure
import com.brbx.domain.model.util.fold
import com.brbx.domain.model.util.success

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