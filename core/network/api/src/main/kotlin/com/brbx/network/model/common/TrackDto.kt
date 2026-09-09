package com.brbx.network.model.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackDto(
    val id: Long,
    val title: String,
    val description: String? = null,
    @SerialName("artwork_url") val artworkUrl: String? = null,
    @SerialName("duration") val durationMs: Long? = null,
    @SerialName("stream_url") val streamUrl: String? = null,
    val user: UserDto? = null
)