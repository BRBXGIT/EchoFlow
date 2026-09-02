package com.brbx.data.repository

interface AuthServerRepository {
    val currentUrl: String?

    fun clearUrl()

    fun openServer()
}