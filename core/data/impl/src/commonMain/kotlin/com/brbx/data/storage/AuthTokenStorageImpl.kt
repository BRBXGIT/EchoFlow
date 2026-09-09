package com.brbx.data.storage

import com.brbx.data.datasource.AuthTokenDataSource
import com.brbx.domain.model.auth.AuthTokens
import com.brbx.network.model.auth.AuthTokensDto
import com.brbx.network.storage.AuthTokenStorage
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first

internal class AuthTokenStorageImpl(
    private val tokenDataSource: AuthTokenDataSource,
) : AuthTokenStorage {
    override suspend fun getTokens(): AuthTokensDto =
        tokenDataSource.tokens.filterNotNull().first().toApi()

    override suspend fun setTokens(tokens: AuthTokensDto) =
        tokenDataSource.saveTokens(tokens.toDomain())

    override suspend fun clearTokens() =
        tokenDataSource.clearTokens()

    private fun AuthTokens.toApi(): AuthTokensDto =
        AuthTokensDto(this.access, this.refresh)

    private fun AuthTokensDto.toDomain(): AuthTokens =
        AuthTokens(this.access, this.refresh)
}
