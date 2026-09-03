package com.brbx.local_server

import com.brbx.core.local_server.impl.AuthConfig
import io.ktor.server.cio.CIO
import io.ktor.server.cio.CIOApplicationEngine
import io.ktor.server.engine.EmbeddedServer
import io.ktor.server.engine.embeddedServer
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.util.url
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import local_server.JvmAuthServer
import kotlin.time.Duration.Companion.milliseconds

internal class JvmAuthServerImpl : JvmAuthServer {
    private var server: EmbeddedServer<CIOApplicationEngine, CIOApplicationEngine.Configuration>? = null

    override fun startServerAndWaitForCode(
        onDataReceived: (url: String) -> Unit,
    ) {
        if (server == null) {
            server = bindServer(onDataReceived)
        }
        server?.start(wait = false)
    }

    private fun bindServer(
        onDataReceived: (url: String) -> Unit,
    ): EmbeddedServer<CIOApplicationEngine, CIOApplicationEngine.Configuration> =
        AuthConfig.jvmPort?.let { port ->
            embeddedServer(factory = CIO, port = port) {
                routing {
                    get(path = AuthConfig.jvmPath) {
                        val url = call.url()
                        if (url.isNotEmpty()) {
                            call.respondText("Done! You can close this tab and open app.")
                            onDataReceived(url)
                            stopServerWithDelay()
                        } else {
                            call.respondText("Something went wrong please retry.")
                            stopServerWithDelay()
                        }
                    }
                }
            }
        } ?: error("Port cannot be null")

    private suspend fun stopServerWithDelay() {
        val currentServer = server
        server = null
        coroutineScope {
            delay(duration = 500.milliseconds)
            currentServer?.stop(gracePeriodMillis = 1000, timeoutMillis = 2000)
        }
    }
}