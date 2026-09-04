package com.brbx.domain.model

data class Track(
    val id: Long,
    val title: String,
    val artworkUrl: String?,
    val streamable: Boolean,
)
