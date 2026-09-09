package com.brbx.data.common

import androidx.paging.PagingData
import androidx.paging.map
import com.brbx.domain.model.common.Track
import com.brbx.domain.model.common.User
import com.brbx.network.model.common.TrackDto
import com.brbx.network.model.common.UserDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal fun Flow<PagingData<TrackDto>>.toTrackFlow(): Flow<PagingData<Track>> =
    this.map { pagingData -> pagingData.map { track -> track.toDomain() } }

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