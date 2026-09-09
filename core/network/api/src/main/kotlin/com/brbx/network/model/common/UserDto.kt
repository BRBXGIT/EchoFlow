package com.brbx.network.model.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Long,
    val username: String,
    @SerialName("avatar_url") val avatarUrl: String? = null,
)