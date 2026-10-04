package com.brbx.data.common

import com.brbx.domain.model.base.ItemsCollection
import com.brbx.domain.model.common.Track
import com.brbx.domain.model.common.User
import com.brbx.network.model.base.CollectionDto
import com.brbx.network.model.base.PaginatedDto
import com.brbx.network.model.common.TrackDto
import com.brbx.network.model.common.UserDto

internal fun <DTO, DOMAIN> CollectionDto<DTO>.toDomain(
    mapper: (DTO) -> DOMAIN,
): ItemsCollection<DOMAIN> = ItemsCollection(collection.map(transform = mapper))

internal fun <DTO, DOMAIN> PaginatedDto<DTO>.toDomain(
    mapper: (DTO) -> DOMAIN,
): ItemsCollection<DOMAIN> = ItemsCollection(collection.map(transform = mapper))

internal fun TrackDto.toDomain(): Track =
    Track(
        id = id,
        title = title,
        artworkUrl = artworkUrl,
        description = description,
        streamUrl = streamUrl,
        streamable = streamUrl?.let { streamable } ?: false,
        duration = durationMs,
        user = user?.toDomain(),
    )

private fun UserDto.toDomain(): User =
    User(
        id = id,
        name = username,
        avatarUrl = avatarUrl,
    )
