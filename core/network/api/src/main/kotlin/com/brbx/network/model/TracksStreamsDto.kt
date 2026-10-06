package com.brbx.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TracksStreamsDto(
    @SerialName("hls_aac_160_url") val hlsAac160: String? = null,
    @SerialName("hls_mp3_128_url") val hlsMp3128: String? = null,
    @SerialName("preview_mp3_128_url") val hlsPreviewMp3128: String? = null,
)
