package com.brbx.network.model.feed

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeedItemDto<T>(
    val type: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
    val origin: T? = null,
)
