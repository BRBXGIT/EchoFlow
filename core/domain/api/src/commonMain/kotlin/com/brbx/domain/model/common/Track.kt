package com.brbx.domain.model.common

import com.brbx.domain.model.enums.ArtworkSize
import com.brbx.domain.model.enums.toArtworkUrl

data class Track(
    val id: Long,
    val title: String,
    val description: String?,
    val artworkUrl: String?,
    val streamable: Boolean,
    val duration: Long?,
    val user: User?,
    val urn: String,
) {
    val mediumResArtworkUrl: String?
        get() = (artworkUrl ?: user?.avatarUrl)?.toArtworkUrl(size = ArtworkSize.MEDIUM)

    val highResArtworkUrl: String?
        get() = (artworkUrl ?: user?.avatarUrl)?.toArtworkUrl(size = ArtworkSize.LARGE)

    // TODO Tracks loading was rewritten in sc api, need to make additional call for the hls
    val streamUrl: String?
        get() = if (streamable) "https://api.soundcloud.com/media/soundcloud:tracks:$id/connections/hls/mp3_128" else null
}
