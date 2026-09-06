package com.brbx.network.model

import com.brbx.network.model.base.PaginatedResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaginatedTracksResponseDto(
    override val collection: List<TrackDto>,
    @SerialName("next_href") override val nextHref: String? = null,
    @SerialName("future_href") override val futureHref: String? = null
) : PaginatedResponse<TrackDto>
