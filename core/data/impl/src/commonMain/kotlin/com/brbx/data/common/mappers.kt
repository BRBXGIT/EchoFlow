package com.brbx.data.common

import com.brbx.domain.model.common.Track
import com.brbx.domain.model.common.User
import com.brbx.network.model.common.TrackDto
import com.brbx.network.model.common.UserDto

internal fun TrackDto.toDomain(): Track =
    Track(
        id = id,
        title = title,
        artworkUrl = artworkUrl,
        description = description,
        user = user?.toDomain(),
    )

private fun UserDto.toDomain(): User =
    User(
        id = id,
        name = username,
        avatarUrl = avatarUrl,
    )