package com.brbx.data.datasource

import com.brbx.domain.model.auth.AuthTokens
import com.brbx.network.model.auth.AuthTokensDto
import com.brbx.network.storage.AuthTokenStorage
import com.brbx.preferences.AuthPrefsManager
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

internal class AuthTokenDataSourceImpl(
    private val authPrefsManager: AuthPrefsManager,
) : AuthTokenDataSource, AuthTokenStorage {

    override val tokens = authPrefsManager.tokens.map { pair ->
        pair?.let { (access, refresh) -> AuthTokens(access = access, refresh = refresh) }
    }

    override suspend fun saveTokens(tokens: AuthTokens) =
        authPrefsManager.saveTokens(access = tokens.access, refresh = tokens.refresh)

    override suspend fun clearTokens() =
        authPrefsManager.clearTokens()

    override suspend fun getTokens(): AuthTokensDto {
        val domainTokens = tokens.filterNotNull().first()
        return AuthTokensDto(access = domainTokens.access, refresh = domainTokens.refresh)
    }

    override suspend fun setTokens(tokens: AuthTokensDto) =
        authPrefsManager.saveTokens(access = tokens.access, refresh = tokens.refresh)
}
