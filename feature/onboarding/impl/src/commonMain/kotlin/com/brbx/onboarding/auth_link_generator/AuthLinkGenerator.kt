package com.brbx.onboarding.auth_link_generator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import com.brbx.feature.onboarding.impl.AuthConfig
import java.security.MessageDigest
import kotlin.io.encoding.Base64
import kotlin.random.Random

@Immutable
internal interface AuthLinkGenerator {
    fun generate(): String
}

private class AuthLinkGeneratorImpl : AuthLinkGenerator {
    private var cachedAuthData: String? = null

    override fun generate(): String {
        return cachedAuthData ?: generateNewAuthData().also { cachedAuthData = it }
    }

    private fun generateNewAuthData(): String {
        val state = PkceUtil.generateRandomString(32)
        val codeVerifier = PkceUtil.generateRandomString(64)
        val codeChallenge = PkceUtil.generateCodeChallenge(codeVerifier)

        val url = buildString {
            append(AuthConfig.authBasePath)
            append("?client_id=${AuthConfig.clientId}")
            append("&redirect_uri=${AuthConfig.redirectUri}")
            append("&response_type=code")
            append("&code_challenge_method=S256")
            append("&code_challenge=$codeChallenge")
            append("&state=$state")
        }

        return url
    }
}

private fun sha256(input: String): ByteArray {
    val digest = MessageDigest.getInstance("SHA-256")
    return digest.digest(input.encodeToByteArray())
}

private object PkceUtil {
    fun generateRandomString(length: Int = 32): String {
        val bytes = Random.nextBytes(length)
        return Base64.UrlSafe.encode(bytes).trimEnd('=')
    }

    fun generateCodeChallenge(verifier: String): String {
        val digestBytes = sha256(verifier)
        return Base64.UrlSafe.encode(digestBytes).trimEnd('=')
    }
}

@Composable
internal fun rememberAuthLinkGenerator(): AuthLinkGenerator =
    remember { AuthLinkGeneratorImpl() }