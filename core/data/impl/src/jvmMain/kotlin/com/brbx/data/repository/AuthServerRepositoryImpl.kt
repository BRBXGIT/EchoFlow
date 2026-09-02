package com.brbx.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import local_server.JvmAuthServer

internal class AuthServerRepositoryImpl(
    private val server: JvmAuthServer,
) : AuthServerRepository {
    private val _currentUrl = MutableStateFlow<String?>(value = null)
    override val currentUrl = _currentUrl.asStateFlow()

    override fun clearUrl() {
        _currentUrl.value = null
    }

    override fun openServer() =
        server.startServerAndWaitForCode(
            onDataReceived = { url -> _currentUrl.value = url },
        )
}