package com.brbx.network.client

import com.brbx.network.api.AuthApi
import com.brbx.network.handler.AuthResponseHandler
import com.brbx.network.model.AuthTokensDto
import com.brbx.network.storage.AuthTokenStorage
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer

internal interface ApiClientProvider : BaseApiClientProvider

internal class ApiClientProviderImpl(
    private val tokenStorage: AuthTokenStorage,
    private val handler: AuthResponseHandler,
    private val authApi: AuthApi,
) : ApiClientProvider {
    override val client by setupApiClient(BaseUrl) {
        install(plugin = Auth) {
            bearer {
                loadTokens {
                    val (access, refresh) = tokenStorage.getTokens()
                    BearerTokens(
                        accessToken = access,
                        refreshToken = refresh,
                    )
                }
                refreshTokens {
                    val refreshToken = tokenStorage.getTokens().refresh
                    val refreshed = handler.handle { authApi.refreshTokens(refreshToken) }
                    val newTokens = AuthTokensDto(
                        access = refreshed.accessToken,
                        refresh = refreshed.refreshToken,
                    )
                    tokenStorage.setTokens(newTokens)
                    BearerTokens(
                        accessToken = newTokens.access,
                        refreshToken = newTokens.refresh,
                    )
                }
            }
        }
    }

    private companion object {
        const val BaseUrl: String = "https://api.soundcloud.com/"
    }
}
