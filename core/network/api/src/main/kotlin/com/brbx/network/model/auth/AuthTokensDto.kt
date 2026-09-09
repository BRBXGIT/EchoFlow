package com.brbx.network.model.auth

data class AuthTokensDto(
    val access: String,
    val refresh: String,
)
