package com.brbx.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaginatedTracksResponseDto(
    val collection: List<TrackDto>,
    @SerialName("next_href") val nextHref: String? = null,
    @SerialName("future_href") val futureHref: String? = null
)
