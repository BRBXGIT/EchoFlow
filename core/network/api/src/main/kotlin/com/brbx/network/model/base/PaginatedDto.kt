package com.brbx.network.model.base

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaginatedDto<T>(
    val collection: List<T>,
    @SerialName("next_href") val nextHref: String? = null,
)