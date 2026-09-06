package com.brbx.domain.use_case

import com.brbx.data.repository.RelatedRepository
import com.brbx.domain.model.paging.TrackFlow

internal class GetPagedRelatedTracksUseCaseImpl(
    private val relatedRepository: RelatedRepository,
) : GetPagedRelatedTracksUseCase {
    override fun invoke(id: Int): TrackFlow =
        relatedRepository.getSimilarTracks(id)
}