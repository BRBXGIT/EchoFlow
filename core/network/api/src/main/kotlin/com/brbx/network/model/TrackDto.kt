package com.brbx.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackDto(
    val id: Long,
    val title: String,
    @SerialName("artwork_url") val artworkUrl: String?,
    @SerialName("streamable") val streamable: Boolean,
)
