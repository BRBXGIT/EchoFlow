package com.brbx.network.client

import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.bearer

internal interface ApiClientProvider : BaseApiClientProvider

internal class ApiClientProviderImpl : ApiClientProvider {

    private companion object {
        const val BaseUrl: String = "https://api.soundcloud.com/"
    }

    override val client by setupApiClient(BaseUrl) {
        install(plugin = Auth) {
            bearer {

            }
        }
    }
}