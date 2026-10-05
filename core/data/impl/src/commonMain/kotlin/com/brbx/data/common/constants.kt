package com.brbx.data.common

internal object NetworkConstants {
    const val AuthHeader = "Authorization"

    fun bearerAuth(token: String) =
        "Bearer $token"
}