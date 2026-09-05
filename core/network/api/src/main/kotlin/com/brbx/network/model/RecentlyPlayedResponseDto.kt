package com.brbx.network.model

import kotlinx.serialization.Serializable

@Serializable
data class RecentlyPlayedResponseDto(
    val collection: List<TrackDto>,
)