package com.brbx.data.builder

interface AuthLinkBuilder {
    val currentCodeVerifier: String?
    val currentState: String?

    fun getLink(): String
    fun clear()
}