package com.brbx.domain.model.enums

enum class ArtworkSize(val suffix: String) {
    MEDIUM(suffix = "-t300x300."),
    LARGE(suffix = "-t500x500."),
}

internal fun String.toArtworkUrl(size: ArtworkSize = ArtworkSize.MEDIUM): String =
    replace(oldValue = "-large.", newValue = size.suffix)
