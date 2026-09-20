package com.brbx.playlist

import com.brbx.navigation.EchoFlowNavKey
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistRoute(
    val playlist: Playlist,
) : EchoFlowNavKey