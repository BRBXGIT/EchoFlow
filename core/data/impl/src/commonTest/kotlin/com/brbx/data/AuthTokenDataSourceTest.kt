package com.brbx.data

import com.brbx.data.datasource.AuthTokenDataSourceImpl
import com.brbx.domain.model.auth.AuthTokens
import com.brbx.preferences.AuthPrefsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class AuthTokenDataSourceTest {

    private val fakeAuthPrefsManager = FakeAuthPrefsManager()
    private val dataSource = AuthTokenDataSourceImpl(authPrefsManager = fakeAuthPrefsManager)

    @Test
    fun `tokens flow emits null initially when no tokens saved`() = runTest {
        val currentTokens = dataSource.tokens.first()
        assertNull(actual = currentTokens)
    }

    @Test
    fun `saveTokens saves access and refresh tokens and tokens flow emits valid AuthTokens`() = runTest {
        val tokensToSave = AuthTokens(access = "access_token_123", refresh = "refresh_token_123")

        dataSource.saveTokens(tokensToSave)

        val updatedTokens = dataSource.tokens.first()
        assertEquals(expected = tokensToSave, actual = updatedTokens)
        assertEquals(expected = "access_token_123", actual = updatedTokens?.access)
        assertEquals(expected = "refresh_token_123", actual = updatedTokens?.refresh)
    }

    @Test
    fun `tokens flow emits null if only accessToken exists`() = runTest {
        fakeAuthPrefsManager.saveAccessToken("only_access_token")

        val currentTokens = dataSource.tokens.first()
        assertNull(actual = currentTokens)
    }

    @Test
    fun `tokens flow emits null if only refreshToken exists`() = runTest {
        fakeAuthPrefsManager.saveRefreshToken("only_refresh_token")

        val currentTokens = dataSource.tokens.first()
        assertNull(actual = currentTokens)
    }

    @Test
    fun `clearTokens removes both tokens and tokens flow emits null`() = runTest {
        val initialTokens = AuthTokens(access = "access_1", refresh = "refresh_1")
        dataSource.saveTokens(initialTokens)
        assertEquals(expected = initialTokens, actual = dataSource.tokens.first())

        dataSource.clearTokens()

        assertNull(actual = dataSource.tokens.first())
    }

    private class FakeAuthPrefsManager : AuthPrefsManager {
        override val accessToken = MutableStateFlow<String?>(null)
        override val refreshToken = MutableStateFlow<String?>(null)

        override suspend fun saveAccessToken(token: String) {
            accessToken.value = token
        }

        override suspend fun saveRefreshToken(token: String) {
            refreshToken.value = token
        }

        override suspend fun clearAccessToken() {
            accessToken.value = null
        }

        override suspend fun clearRefreshToken() {
            refreshToken.value = null
        }
    }
}
