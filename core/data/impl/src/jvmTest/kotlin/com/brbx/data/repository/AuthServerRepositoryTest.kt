package com.brbx.data.repository

import local_server.JvmAuthServer
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class AuthServerRepositoryTest {

    private val fakeJvmAuthServer = FakeJvmAuthServer()
    private val repository = AuthServerRepositoryImpl(server = fakeJvmAuthServer)

    @Test
    fun `initial currentUrl is null`() {
        val initialUrl = repository.currentUrl.value

        assertNull(actual = initialUrl)
    }

    @Test
    fun `openServer starts server and updates currentUrl when data is received`() {
        val receivedAuthUrl = "http://localhost:8080/callback?code=jvm_code_123&state=jvm_state_456"
        fakeJvmAuthServer.urlToEmit = receivedAuthUrl

        repository.openServer()

        val updatedUrl = repository.currentUrl.value
        assertEquals(expected = receivedAuthUrl, actual = updatedUrl)
    }

    @Test
    fun `clearUrl resets currentUrl back to null`() {
        fakeJvmAuthServer.urlToEmit = "http://localhost:8080/callback?code=test&state=test"
        repository.openServer()
        assertEquals(expected = "http://localhost:8080/callback?code=test&state=test", actual = repository.currentUrl.value)

        repository.clearUrl()

        assertNull(actual = repository.currentUrl.value)
    }

    private class FakeJvmAuthServer : JvmAuthServer {
        var urlToEmit: String? = null

        override fun startServerAndWaitForCode(onDataReceived: (url: String) -> Unit) {
            urlToEmit?.let { url ->
                onDataReceived(url)
            }
        }
    }
}
