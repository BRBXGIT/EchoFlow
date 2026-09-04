package com.brbx.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecentlyPlayedResponseDto(
    @SerialName("collection") val collection: List<RecentlyPlayedItemDto>,
    @SerialName("next_href") val nextHref: String?,
) {
    @Serializable
    data class RecentlyPlayedItemDto(
        @SerialName("played_at") val playedAt: String?,
        @SerialName("track") val track: TrackDto,
    )
}