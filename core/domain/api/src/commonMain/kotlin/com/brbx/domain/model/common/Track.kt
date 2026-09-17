package com.brbx.domain.model.common

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
        get() = (artworkUrl ?: user?.avatarUrl)
            ?.replace(oldValue = "-large.", newValue = "-t300x300.")
}
