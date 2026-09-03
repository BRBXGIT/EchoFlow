package com.brbx.network.client

import com.brbx.network.api.AuthApi
import com.brbx.network.inversion.TokensInteractor
import com.brbx.network.model.Tokens
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer

internal interface ApiClientProvider : BaseApiClientProvider

internal class ApiClientProviderImpl(
    private val tokensInteractor: TokensInteractor,
    private val authApi: AuthApi,
) : ApiClientProvider {
    override val client by setupApiClient(BaseUrl) {
        install(plugin = Auth) {
            bearer {
                loadTokens {
                    val tokens = tokensInteractor.getTokens()
                    BearerTokens(
                        accessToken = tokens.access,
                        refreshToken = tokens.refresh,
                    )
                }
                refreshTokens {
                    val refreshToken = tokensInteractor.getTokens().refresh
                    val refreshed = authApi.refreshTokens(refreshToken)
                    val tokens = Tokens(
                        access = refreshed.accessToken,
                        refresh = refreshed.refreshToken,
                    )
                    tokensInteractor.setTokens(tokens)
                    BearerTokens(
                        accessToken = tokens.access,
                        refreshToken = tokens.refresh,
                    )
                }
            }
        }
    }

    private companion object {
        const val BaseUrl: String = "https://api.soundcloud.com/"
    }
}