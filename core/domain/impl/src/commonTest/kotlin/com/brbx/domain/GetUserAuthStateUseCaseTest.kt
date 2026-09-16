package com.brbx.domain

import com.brbx.data.repository.AuthRepository
import com.brbx.domain.model.auth.AuthTokens
import com.brbx.domain.model.common.RequestResult
import com.brbx.domain.model.common.failure
import com.brbx.domain.model.enums.RequestException
import com.brbx.domain.model.enums.UserAuthState
import com.brbx.domain.use_case.GetUserAuthStateUseCaseImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetUserAuthStateUseCaseTest {

    private val fakeAuthRepository = FakeAuthRepository()
    private val useCase = GetUserAuthStateUseCaseImpl(repository = fakeAuthRepository)

    @Test
    fun `invoke returns userAuthState flow from repository`() = runTest {
        fakeAuthRepository.authStateFlow.value = UserAuthState.Unauthorized
        assertEquals(expected = UserAuthState.Unauthorized, actual = useCase().first())

        fakeAuthRepository.authStateFlow.value = UserAuthState.Authorized
        assertEquals(expected = UserAuthState.Authorized, actual = useCase().first())
    }

    private class FakeAuthRepository : AuthRepository {
        val authStateFlow = MutableStateFlow(value = UserAuthState.Unauthorized)
        override val userAuthState = authStateFlow

        override suspend fun authenticate(code: String, verifier: String): RequestResult<AuthTokens> =
            failure(exception = RequestException.Unknown)
    }
}
