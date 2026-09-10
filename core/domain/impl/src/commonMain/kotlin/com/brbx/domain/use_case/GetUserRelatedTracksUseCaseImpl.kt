package com.brbx.domain.use_case

import com.brbx.data.repository.RelatedRepository
import com.brbx.data.repository.UserHistoryRepository
import com.brbx.domain.model.common.Track
import com.brbx.domain.pagination.Paginator
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first

internal class GetUserRelatedTracksUseCaseImpl(
    private val historyRepository: UserHistoryRepository,
    private val relatedRepository: RelatedRepository,
) : GetUserRelatedTracksUseCase {
    override suspend fun invoke(): Paginator<Track> =
        historyRepository.recentTracks
            .filterNotNull()
            .first { it.collection.isNotEmpty() }
            .run { relatedRepository.getSimilarTracksPaginator(collection.first().id) }
}