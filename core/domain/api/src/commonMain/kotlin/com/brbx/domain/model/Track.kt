package com.brbx.domain.model

data class Track(
    val id: Long,
    val title: String,
    val description: String?,
    val artworkUrl: String?,
    val user: User?,
) {
    val highResArtworkUrl: String?
        get() = (artworkUrl ?: user?.avatarUrl)
            ?.replace(oldValue = "-large.", newValue = "-t500x500.")
}
