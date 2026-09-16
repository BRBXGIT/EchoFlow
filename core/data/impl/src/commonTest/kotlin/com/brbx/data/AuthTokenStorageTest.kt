package com.brbx.data

import com.brbx.data.datasource.AuthTokenDataSource
import com.brbx.data.storage.AuthTokenStorageImpl
import com.brbx.domain.model.auth.AuthTokens
import com.brbx.network.model.auth.AuthTokensDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class AuthTokenStorageTest {

    private val fakeTokenDataSource = FakeAuthTokenDataSource()
    private val storage = AuthTokenStorageImpl(tokenDataSource = fakeTokenDataSource)

    @Test
    fun `getTokens returns mapped AuthTokensDto when tokens exist in dataSource`() = runTest {
        val domainTokens = AuthTokens(access = "access_token_123", refresh = "refresh_token_456")
        fakeTokenDataSource.saveTokens(tokens = domainTokens)

        val dtoTokens = storage.getTokens()

        assertEquals(expected = "access_token_123", actual = dtoTokens.access)
        assertEquals(expected = "refresh_token_456", actual = dtoTokens.refresh)
    }

    @Test
    fun `setTokens maps Dto to Domain and saves into dataSource`() = runTest {
        val dtoToSave = AuthTokensDto(access = "new_access_token", refresh = "new_refresh_token")

        storage.setTokens(tokens = dtoToSave)

        val currentDomainTokens = fakeTokenDataSource.tokens.first()
        assertEquals(expected = "new_access_token", actual = currentDomainTokens?.access)
        assertEquals(expected = "new_refresh_token", actual = currentDomainTokens?.refresh)
    }

    @Test
    fun `clearTokens delegates clearing to dataSource`() = runTest {
        val initialDto = AuthTokensDto(access = "acc", refresh = "ref")
        storage.setTokens(tokens = initialDto)

        storage.clearTokens()

        val currentDomainTokens = fakeTokenDataSource.tokens.first()
        assertNull(actual = currentDomainTokens)
    }

    private class FakeAuthTokenDataSource : AuthTokenDataSource {
        private val _tokens = MutableStateFlow<AuthTokens?>(value = null)
        override val tokens: Flow<AuthTokens?> = _tokens

        override suspend fun saveTokens(tokens: AuthTokens) {
            _tokens.value = tokens
        }

        override suspend fun clearTokens() {
            _tokens.value = null
        }
    }
}