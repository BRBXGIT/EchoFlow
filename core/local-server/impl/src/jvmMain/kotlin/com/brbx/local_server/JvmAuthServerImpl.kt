package com.brbx.local_server

import io.ktor.server.cio.CIO
import io.ktor.server.cio.CIOApplicationEngine
import io.ktor.server.engine.EmbeddedServer
import io.ktor.server.engine.embeddedServer
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.util.url
import local_server.JvmAuthServer

internal class JvmAuthServerImpl : JvmAuthServer {
    private var server: EmbeddedServer<CIOApplicationEngine, CIOApplicationEngine.Configuration>? = null

    override fun startServerAndWaitForCode(
        onDataReceived: (url: String) -> Unit,
    ) {
        server?.start() ?: {
            bindServer(onDataReceived)
            server?.start()
        }
    }

    private fun bindServer(
        onDataReceived: (url: String) -> Unit,
    ): EmbeddedServer<CIOApplicationEngine, CIOApplicationEngine.Configuration> =
        embeddedServer(factory = CIO, port = 53999) {
            routing {
                get(path = "/callback") {
                    val url = call.url()
                    if (url != "") {
                        call.respondText("Done! You can close this tab and open app.")
                        onDataReceived(url)
                        server?.stop()
                        server = null
                    } else {
                        call.respondText("Something went wrong please retry.")
                        server?.stop()
                    }
                }
            }
        }
}