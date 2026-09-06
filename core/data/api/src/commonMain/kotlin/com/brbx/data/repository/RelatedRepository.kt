package com.brbx.data.repository

import com.brbx.domain.model.paging.TrackFlow

interface RelatedRepository {
    fun getSimilarTracks(id: Int): TrackFlow
}