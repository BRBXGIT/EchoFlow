package com.brbx.domain.model

data class AuthCallbackPayload(
    val code: String,
    val state: String,
)