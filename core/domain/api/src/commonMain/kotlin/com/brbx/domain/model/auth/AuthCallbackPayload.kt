package com.brbx.domain.model.auth

data class AuthCallbackPayload(
    val code: String,
    val state: String,
)