package com.brbx.network.handler

import io.ktor.client.plugins.ClientRequestException

internal interface AuthResponseHandler {
    fun <T> handle(call: suspend () -> T): T
}

internal class AuthResponseHandlerImpl : AuthResponseHandler {
    override fun <T> handle(call: suspend () -> T): T {
        try {

        } catch (e: ClientRequestException) {
            
        }
    }
}