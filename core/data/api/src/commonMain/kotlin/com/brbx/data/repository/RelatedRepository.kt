package com.brbx.data.repository

import com.brbx.domain.model.common.Track
import com.brbx.domain.pagination.Paginator

interface RelatedRepository {
    suspend fun getSimilarTracksPaginator(id: Long): Paginator<Track>
}