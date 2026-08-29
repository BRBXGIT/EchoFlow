package com.brbx.network.client

import io.ktor.client.HttpClient

internal interface BaseApiClientProvider {

    val client: HttpClient
}