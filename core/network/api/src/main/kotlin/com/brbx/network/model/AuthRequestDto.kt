package com.brbx.network.model

import com.brbx.core.network.api.AuthConfig

data class AuthRequestDto(
    val code: String,
    val codeVerifier: String,
    val clientId: String = AuthConfig.clientId,
    val clientSecret: String = AuthConfig.clientSecret,
    val redirectUri: String = AuthConfig.redirectUri,
    val grantType: String = AuthConfig.authGrantType
)