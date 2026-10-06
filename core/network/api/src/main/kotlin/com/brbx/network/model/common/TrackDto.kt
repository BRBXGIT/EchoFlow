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
    val streamable: Boolean = false,
    val user: UserDto? = null,
    val urn: String,
)