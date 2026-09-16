package com.brbx.data

import com.brbx.data.handler.NetworkResponseHandlerImpl
import com.brbx.data.repository.AuthRepositoryImpl
import com.brbx.domain.model.common.RequestResult
import com.brbx.domain.model.enums.RequestException
import com.brbx.domain.model.enums.UserAuthState
import com.brbx.network.api.AuthApi
import com.brbx.network.model.auth.AuthRequestDto
import com.brbx.network.model.auth.AuthResponseDto
import com.brbx.preferences.AuthPrefsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.io.IOException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AuthRepositoryTest {

    private val fakeAuthPrefsManager = FakeAuthPrefsManager()
    private val fakeAuthApi = FakeAuthApi()
    private val handler = NetworkResponseHandlerImpl()

    private val repository = AuthRepositoryImpl(
        authPrefs = fakeAuthPrefsManager,
        authApi = fakeAuthApi,
        handler = handler,
    )

    @Test
    fun `userAuthState emits Unauthorized when tokens are null`() = runTest {
        val authState = repository.userAuthState.first()

        assertEquals(expected = UserAuthState.Unauthorized, actual = authState)
    }

    @Test
    fun `userAuthState emits Authorized when accessToken is present`() = runTest {
        fakeAuthPrefsManager.saveAccessToken(token = "valid_access_token")

        val authState = repository.userAuthState.first()

        assertEquals(expected = UserAuthState.Authorized, actual = authState)
    }

    @Test
    fun `userAuthState emits Authorized when refreshToken is present`() = runTest {
        fakeAuthPrefsManager.saveRefreshToken(token = "valid_refresh_token")

        val authState = repository.userAuthState.first()

        assertEquals(expected = UserAuthState.Authorized, actual = authState)
    }

    @Test
    fun `authenticate returns AuthTokens on successful code exchange`() = runTest {
        fakeAuthApi.responseToReturn = AuthResponseDto(
            accessToken = "access_123",
            refreshToken = "refresh_456",
            expiresIn = 3600,
            scope = "*",
        )

        val result = repository.authenticate(code = "auth_code", verifier = "pkce_verifier")

        assertTrue(actual = result is RequestResult.Success)
        val tokens = result.value
        assertEquals(expected = "access_123", actual = tokens.access)
        assertEquals(expected = "refresh_456", actual = tokens.refresh)
        assertEquals(expected = "auth_code", actual = fakeAuthApi.lastRequestDto?.code)
        assertEquals(expected = "pkce_verifier", actual = fakeAuthApi.lastRequestDto?.codeVerifier)
    }

    @Test
    fun `authenticate returns RequestException on network error`() = runTest {
        fakeAuthApi.shouldThrowIOException = true

        val result = repository.authenticate(code = "auth_code", verifier = "pkce_verifier")

        assertTrue(actual = result is RequestResult.Exception)
        val exception = result.value
        assertEquals(expected = RequestException.Internet, actual = exception)
    }

    private class FakeAuthPrefsManager : AuthPrefsManager {
        override val accessToken = MutableStateFlow<String?>(value = null)
        override val refreshToken = MutableStateFlow<String?>(value = null)

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

    private class FakeAuthApi : AuthApi {
        var responseToReturn: AuthResponseDto = AuthResponseDto(
            accessToken = "default",
            refreshToken = "default",
            expiresIn = 3600,
            scope = "*",
        )
        var lastRequestDto: AuthRequestDto? = null
        var shouldThrowIOException: Boolean = false

        override suspend fun exchangeCode(authRequest: AuthRequestDto): AuthResponseDto {
            if (shouldThrowIOException) throw IOException("Network error")
            lastRequestDto = authRequest
            return responseToReturn
        }

        override suspend fun refreshTokens(refreshToken: String): AuthResponseDto {
            if (shouldThrowIOException) throw IOException("Network error")
            return responseToReturn
        }
    }
}
