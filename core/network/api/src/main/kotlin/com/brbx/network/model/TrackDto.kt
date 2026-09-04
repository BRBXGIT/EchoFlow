package com.brbx.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackDto(
    @SerialName("id") val id: Long,
    @SerialName("title") val title: String,
    @SerialName("artwork_url") val artworkUrl: String?,
    @SerialName("streamable") val streamable: Boolean,
)
