package com.brbx.data

import com.brbx.core.data.impl.AuthConfig
import com.brbx.data.handler.AuthCallbackHandlerImpl
import com.brbx.domain.model.auth.AuthCallbackPayload
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class AuthCallbackHandlerTest {

    private val handler = AuthCallbackHandlerImpl()

    @Test
    fun `handle returns payload when valid Android redirect URI with code and state is provided`() {
        val rawUri = "${AuthConfig.androidRedirectUri}?code=android_code_123&state=android_state_456"

        val payload = handler.handle(rawUri)

        assertEquals(
            expected = AuthCallbackPayload(code = "android_code_123", state = "android_state_456"),
            actual = payload
        )
    }

    @Test
    fun `handle returns payload when valid JVM redirect URI with code and state is provided`() {
        val jvmUri = "${AuthConfig.jvmScheme}${AuthConfig.jvmHost}:${AuthConfig.jvmPort}${AuthConfig.jvmPath}"
        val rawUri = "$jvmUri?code=jvm_code_789&state=jvm_state_abc"

        val payload = handler.handle(rawUri)

        assertEquals(
            expected = AuthCallbackPayload(code = "jvm_code_789", state = "jvm_state_abc"),
            actual = payload
        )
    }

    @Test
    fun `handle returns null when URI has invalid scheme or prefix`() {
        val maliciousUri = "https://malicious-website.com/auth/callback?code=hacked_code&state=hacked_state"

        val payload = handler.handle(maliciousUri)

        assertNull(actual = payload)
    }

    @Test
    fun `handle returns null when code parameter is missing`() {
        val rawUri = "${AuthConfig.androidRedirectUri}?state=only_state_present"

        val payload = handler.handle(rawUri)

        assertNull(actual = payload)
    }

    @Test
    fun `handle returns null when state parameter is missing`() {
        val rawUri = "${AuthConfig.androidRedirectUri}?code=only_code_present"

        val payload = handler.handle(rawUri)

        assertNull(actual = payload)
    }

    @Test
    fun `handle parses code and state correctly when additional query parameters are present`() {
        val rawUri = "${AuthConfig.androidRedirectUri}?code=code_123&extra=ignored_param&state=state_456"

        val payload = handler.handle(rawUri)

        assertEquals(expected = AuthCallbackPayload(code = "code_123", state = "state_456"), actual = payload)
    }
}
