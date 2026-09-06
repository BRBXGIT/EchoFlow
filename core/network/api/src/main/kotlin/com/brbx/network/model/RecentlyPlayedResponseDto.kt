package com.brbx.network.model

import com.brbx.network.model.base.PaginatedResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecentlyPlayedResponseDto(
    override val collection: List<TrackDto>,
    @SerialName("next_href") override val nextHref: String? = null,
    @SerialName("future_href") override val futureHref: String? = null
) : PaginatedResult<TrackDto>