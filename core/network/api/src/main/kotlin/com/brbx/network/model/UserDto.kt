package com.brbx.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("id") val id: Long,
    @SerialName("username") val username: String,
    @SerialName("avatar_url") val avatarUrl: String?,
)
