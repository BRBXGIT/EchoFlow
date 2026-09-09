package com.brbx.domain.model.auth

data class AuthTokens(
    val access: String,
    val refresh: String,
)
