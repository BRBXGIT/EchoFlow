package com.brbx.domain.model.common

import com.brbx.domain.model.enums.ArtworkSize
import com.brbx.domain.model.enums.toArtworkUrl

data class Track(
    val id: Long,
    val title: String,
    val description: String?,
    val artworkUrl: String?,
    val streamUrl: String?,
    val streamable: Boolean,
    val duration: Long?,
    val user: User?,
) {
    val highResArtworkUrl: String?
        get() = (artworkUrl ?: user?.avatarUrl)?.toArtworkUrl(size = ArtworkSize.MEDIUM)
}
