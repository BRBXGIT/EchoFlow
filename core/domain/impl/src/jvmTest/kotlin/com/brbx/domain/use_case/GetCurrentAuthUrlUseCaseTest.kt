package com.brbx.domain.use_case

import com.brbx.data.repository.AuthServerRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetCurrentAuthUrlUseCaseTest {

    private val fakeAuthServerRepository = FakeAuthServerRepository()
    private val useCase = GetCurrentAuthUrlUseCaseImpl(authServerRepository = fakeAuthServerRepository)

    @Test
    fun `invoke waits for non-null currentUrl, clears url in repository and returns received url`() = runTest {
        val expectedUrl = "http://localhost:8080/callback?code=jvm_code&state=jvm_state"

        val deferredResult = async { useCase() }

        fakeAuthServerRepository.currentUrlState.value = expectedUrl

        val actualUrl = deferredResult.await()

        assertEquals(expected = expectedUrl, actual = actualUrl)
        assertTrue(actual = fakeAuthServerRepository.wasClearUrlCalled)
        assertEquals(expected = null, actual = fakeAuthServerRepository.currentUrlState.value)
    }

    private class FakeAuthServerRepository : AuthServerRepository {
        val currentUrlState = MutableStateFlow<String?>(value = null)
        override val currentUrl = currentUrlState
        var wasClearUrlCalled: Boolean = false

        override fun clearUrl() {
            wasClearUrlCalled = true
            currentUrlState.value = null
        }

        override fun openServer() {}
    }
}
