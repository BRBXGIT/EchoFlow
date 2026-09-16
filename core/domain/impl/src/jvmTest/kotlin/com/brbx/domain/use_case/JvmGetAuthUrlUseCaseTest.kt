package com.brbx.domain.use_case

import com.brbx.data.builder.AuthUrlBuilder
import com.brbx.data.repository.AuthServerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class JvmGetAuthUrlUseCaseTest {

    private val fakeAuthUrlBuilder = FakeAuthUrlBuilder()
    private val fakeAuthServerRepository = FakeAuthServerRepository()

    private val useCase = JvmGetAuthUrlUseCaseImpl(
        builder = fakeAuthUrlBuilder,
        serverRepository = fakeAuthServerRepository,
    )

    @Test
    fun `invoke opens auth server and returns built url`() {
        val expectedBuiltUrl = "https://auth.example.com/oauth?client_id=123"
        fakeAuthUrlBuilder.urlToReturn = expectedBuiltUrl

        val actualUrl = useCase()

        assertEquals(expected = expectedBuiltUrl, actual = actualUrl)
        assertTrue(actual = fakeAuthServerRepository.wasOpenServerCalled)
    }

    private class FakeAuthUrlBuilder : AuthUrlBuilder {
        override var currentCodeVerifier: String? = null
        override var currentState: String? = null
        var urlToReturn: String = "https://auth.example.com"

        override fun buildUrl(): String = urlToReturn

        override fun clear() {
            currentCodeVerifier = null
            currentState = null
        }
    }

    private class FakeAuthServerRepository : AuthServerRepository {
        override val currentUrl = MutableStateFlow<String?>(value = null)
        var wasOpenServerCalled: Boolean = false

        override fun clearUrl() {
            currentUrl.value = null
        }

        override fun openServer() {
            wasOpenServerCalled = true
        }
    }
}
