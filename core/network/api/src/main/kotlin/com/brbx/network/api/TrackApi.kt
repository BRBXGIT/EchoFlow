package com.brbx.network.api

import com.brbx.network.model.TracksStreamsDto

interface TrackApi {
    suspend fun getTrackStreams(urn: String): TracksStreamsDto
}