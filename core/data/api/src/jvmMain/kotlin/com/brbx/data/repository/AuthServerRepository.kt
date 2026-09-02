package com.brbx.data.repository

import kotlinx.coroutines.flow.StateFlow

interface AuthServerRepository {
    val currentUrl: StateFlow<String?>

    fun clearUrl()

    fun openServer()
}