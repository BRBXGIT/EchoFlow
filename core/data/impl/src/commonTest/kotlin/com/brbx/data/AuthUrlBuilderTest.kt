package com.brbx.data

import com.brbx.core.data.impl.AuthConfig
import com.brbx.data.builder.AuthUrlBuilderImpl
import com.brbx.data.builder.PkceGenerator
import io.ktor.http.Url
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class AuthUrlBuilderTest {

    private class FakePkceGenerator : PkceGenerator {
        override fun generateRandomString(length: Int): String = "fake_random_string_length_$length"
        override fun generateCodeChallenge(verifier: String): String = "fake_challenge_for_$verifier"
    }

    private val generator = FakePkceGenerator()
    private val builder = AuthUrlBuilderImpl(pkceGenerator = generator)

    @Test
    fun `buildUrl correctly generates URL with all query parameters`() {
        val urlString = builder.buildUrl()
        val parsedUrl = Url(urlString)

        assertEquals(expected = AuthConfig.clientId, actual = parsedUrl.parameters["client_id"])
        assertEquals(expected = AuthConfig.androidRedirectUri, actual = parsedUrl.parameters["redirect_uri"])
        assertEquals(expected = "code", actual = parsedUrl.parameters["response_type"])
        assertEquals(expected = "S256", actual = parsedUrl.parameters["code_challenge_method"])

        val expectedVerifier = "fake_random_string_length_64"
        val expectedChallenge = "fake_challenge_for_$expectedVerifier"

        assertEquals(expectedVerifier, actual = builder.currentCodeVerifier)
        assertEquals(expectedChallenge, actual = parsedUrl.parameters["code_challenge"])

        assertNotNull(actual = builder.currentState)
        assertEquals(expected = builder.currentState, actual = parsedUrl.parameters["state"])
    }

    @Test
    fun `buildUrl caches URL on consecutive calls`() {
        val firstUrl = builder.buildUrl()
        val secondUrl = builder.buildUrl()

        assertEquals(expected = firstUrl, actual = secondUrl)
    }

    @Test
    fun `clear resets state and codeVerifier allowing new URL generation`() {
        builder.buildUrl()
        assertNotNull(actual = builder.currentCodeVerifier)
        assertNotNull(actual = builder.currentState)

        builder.clear()

        assertNull(actual = builder.currentCodeVerifier)
        assertNull(actual = builder.currentState)
    }
}