package com.brbx.data.repository

import com.brbx.domain.model.`typealias`.TrackFlow

interface RelatedRepository {
    fun getSimilarTracks(id: Long): TrackFlow
}