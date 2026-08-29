package com.brbx.network.client

internal interface AuthApiClientProvider : BaseApiClientProvider

internal class AuthApiClientProviderImpl : AuthApiClientProvider {

    private companion object {
        const val BaseUrl: String = "https://secure.soundcloud.com/"
    }

    override val client by setupApiClient(BaseUrl)
}