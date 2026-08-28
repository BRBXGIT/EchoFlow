package com.brbx.data.builder

import okio.ByteString.Companion.encodeUtf8
import kotlin.io.encoding.Base64
import kotlin.random.Random

internal interface PkceGenerator {
    fun generateRandomString(length: Int = 32): String

    fun generateCodeChallenge(verifier: String): String
}

internal class PkceGeneratorImpl : PkceGenerator {
    override fun generateRandomString(length: Int): String =
        Base64.UrlSafe.encode(source = Random.nextBytes(size = length)).trimEnd('=')

    override fun generateCodeChallenge(verifier: String): String =
        Base64.UrlSafe.encode(source = sha256(input = verifier)).trimEnd('=')

    private fun sha256(input: String): ByteArray =
        input.encodeUtf8().sha256().toByteArray()
}