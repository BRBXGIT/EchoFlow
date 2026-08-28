package com.brbx.data.builder

interface AuthLinkBuilder {
    var currentCodeVerifier: String?

    fun getLink(): String
}