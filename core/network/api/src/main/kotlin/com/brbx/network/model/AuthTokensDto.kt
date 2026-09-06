package com.brbx.network.model

data class AuthTokensDto(
    val access: String,
    val refresh: String,
)
