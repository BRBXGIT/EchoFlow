package com.brbx.data.repository

import local_server.JvmAuthServer

internal class AuthServerRepositoryImpl(
    private val server: JvmAuthServer,
) : AuthServerRepository {
    override var currentUrl: String? = null

    override fun clearUrl() {
        currentUrl = null
    }

    override fun openServer() =
        server.startServerAndWaitForCode(
            onDataReceived = { url -> currentUrl = url },
        )
}