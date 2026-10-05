package com.brbx.data.common

internal object NetworkConstants {
    const val AuthHeader = "Authentication"

    fun bearerAuth(token: String) =
        "Bearer $token"
}