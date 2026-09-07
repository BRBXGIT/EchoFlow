package com.brbx.domain.model

import com.brbx.domain.model.`typealias`.TrackFlow
import com.brbx.domain.model.`typealias`.TrackList

data class UserFeed(
    val recentlyPlayed: TrackList,
    val relatedToRecently: TrackFlow?,
)
