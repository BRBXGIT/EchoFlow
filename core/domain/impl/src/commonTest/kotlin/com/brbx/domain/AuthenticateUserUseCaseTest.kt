package com.brbx.domain

import com.brbx.data.builder.AuthUrlBuilder
import com.brbx.data.datasource.AuthTokenDataSource
import com.brbx.data.handler.AuthCallbackHandler
import com.brbx.data.repository.AuthRepository
import com.brbx.domain.model.auth.AuthCallbackPayload
import com.brbx.domain.model.auth.AuthTokens
import com.brbx.domain.model.common.RequestResult
import com.brbx.domain.model.common.failure
import com.brbx.domain.model.common.success
import com.brbx.domain.model.enums.RequestException
import com.brbx.domain.model.enums.UserAuthState
import com.brbx.domain.use_case.AuthenticateUserUseCaseImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AuthenticateUserUseCaseTest {

    private val fakeAuthCallbackHandler = FakeAuthCallbackHandler()
    private val fakeAuthUrlBuilder = FakeAuthUrlBuilder()
    private val fakeAuthRepository = FakeAuthRepository()
    private val fakeAuthTokenDataSource = FakeAuthTokenDataSource()

    private val useCase = AuthenticateUserUseCaseImpl(
        authCallbackHandler = fakeAuthCallbackHandler,
        authUrlBuilder = fakeAuthUrlBuilder,
        authRepository = fakeAuthRepository,
        authTokenDataSource = fakeAuthTokenDataSource,
    )

    @Test
    fun `when rawUri cannot be handled then returns Unknown failure`() = runTest {
        fakeAuthCallbackHandler.payloadToReturn = null

        val result = useCase(rawUri = "invalid_uri")

        assertTrue(actual = result is RequestResult.Exception)
        assertEquals(expected = RequestException.Unknown, actual = result.value)
    }

    @Test
    fun `when state in callback does not match savedState in AuthUrlBuilder then returns CsrfAttack failure and clears builder`() = runTest {
        fakeAuthCallbackHandler.payloadToReturn = AuthCallbackPayload(code = "auth_code", state = "ATTACKER_STATE")
        fakeAuthUrlBuilder.currentState = "LEGITIMATE_STATE"

        val result = useCase(rawUri = "echoflow://auth?code=auth_code&state=ATTACKER_STATE")

        assertTrue(actual = result is RequestResult.Exception)
        assertEquals(expected = RequestException.CsrfAttack, actual = result.value)
        assertTrue(actual = fakeAuthUrlBuilder.wasClearCalled)
        assertFalse(actual = fakeAuthRepository.wasAuthenticateCalled)
    }

    @Test
    fun `when savedVerifier is null then returns Unknown failure`() = runTest {
        fakeAuthCallbackHandler.payloadToReturn = AuthCallbackPayload(code = "auth_code", state = "VALID_STATE")
        fakeAuthUrlBuilder.currentState = "VALID_STATE"
        fakeAuthUrlBuilder.currentCodeVerifier = null

        val result = useCase(rawUri = "echoflow://auth?code=auth_code&state=VALID_STATE")

        assertTrue(actual = result is RequestResult.Exception)
        assertEquals(expected = RequestException.Unknown, actual = result.value)
        assertFalse(actual = fakeAuthRepository.wasAuthenticateCalled)
    }

    @Test
    fun `when authentication succeeds then saves tokens and returns Unit Success`() = runTest {
        fakeAuthCallbackHandler.payloadToReturn = AuthCallbackPayload(code = "valid_code", state = "valid_state")
        fakeAuthUrlBuilder.currentState = "valid_state"
        fakeAuthUrlBuilder.currentCodeVerifier = "pkce_verifier_64_bytes"
        fakeAuthRepository.responseToReturn = success(value = AuthTokens(access = "access_123", refresh = "refresh_456"))

        val result = useCase(rawUri = "echoflow://auth?code=valid_code&state=valid_state")

        assertTrue(actual = result is RequestResult.Success)
        assertEquals(expected = AuthTokens(access = "access_123", refresh = "refresh_456"), actual = fakeAuthTokenDataSource.savedTokens)
    }

    @Test
    fun `when authRepository returns failure then does not save tokens and returns failure`() = runTest {
        fakeAuthCallbackHandler.payloadToReturn = AuthCallbackPayload(code = "code", state = "state")
        fakeAuthUrlBuilder.currentState = "state"
        fakeAuthUrlBuilder.currentCodeVerifier = "verifier"
        fakeAuthRepository.responseToReturn = failure(exception = RequestException.Unauthorized)

        val result = useCase(rawUri = "echoflow://auth?code=code&state=state")

        assertTrue(actual = result is RequestResult.Exception)
        assertEquals(expected = RequestException.Unauthorized, actual = result.value)
        assertEquals(expected = null, actual = fakeAuthTokenDataSource.savedTokens)
    }

    private class FakeAuthCallbackHandler : AuthCallbackHandler {
        var payloadToReturn: AuthCallbackPayload? = null

        override fun handle(rawUri: String): AuthCallbackPayload? = payloadToReturn
    }

    private class FakeAuthUrlBuilder : AuthUrlBuilder {
        override var currentCodeVerifier: String? = null
        override var currentState: String? = null
        var wasClearCalled: Boolean = false

        override fun buildUrl(): String = "https://auth.example.com"

        override fun clear() {
            wasClearCalled = true
            currentCodeVerifier = null
            currentState = null
        }
    }

    private class FakeAuthRepository : AuthRepository {
        override val userAuthState = MutableStateFlow(value = UserAuthState.Unauthorized)
        var responseToReturn: RequestResult<AuthTokens> = failure(exception = RequestException.Unknown)
        var wasAuthenticateCalled: Boolean = false

        override suspend fun authenticate(code: String, verifier: String): RequestResult<AuthTokens> {
            wasAuthenticateCalled = true
            return responseToReturn
        }
    }

    private class FakeAuthTokenDataSource : AuthTokenDataSource {
        override val tokens = MutableStateFlow<AuthTokens?>(value = null)
        var savedTokens: AuthTokens? = null

        override suspend fun saveTokens(tokens: AuthTokens) {
            savedTokens = tokens
            this.tokens.value = tokens
        }

        override suspend fun clearTokens() {
            savedTokens = null
            tokens.value = null
        }
    }
}
