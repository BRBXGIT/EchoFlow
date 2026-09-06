package com.brbx.data.builder

interface AuthUrlBuilder {
    val currentCodeVerifier: String?
    val currentState: String?

    fun buildUrl(): String
    fun clear()
}
