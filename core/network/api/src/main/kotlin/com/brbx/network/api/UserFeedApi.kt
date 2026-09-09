package com.brbx.network.api

import com.brbx.network.model.base.CollectionDto
import com.brbx.network.model.common.TrackDto

interface UserFeedApi {
    suspend fun getRecentlyPlayedTracks(): CollectionDto<TrackDto>
}
