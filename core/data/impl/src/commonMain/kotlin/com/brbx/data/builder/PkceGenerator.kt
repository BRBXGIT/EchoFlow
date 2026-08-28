package com.brbx.data.builder

import okio.ByteString.Companion.encodeUtf8
import kotlin.io.encoding.Base64
import kotlin.random.Random

internal interface PkceGenerator {
    fun generateRandomString(length: Int = 32): String

    fun generateCodeChallenge(verifier: String): String
}

internal class PkceGeneratorImpl : PkceGenerator {
    override fun generateRandomString(length: Int): String {
        val bytes = Random.nextBytes(size = length)
        return Base64.UrlSafe.encode(source = bytes).trimEnd('=')
    }

    override fun generateCodeChallenge(verifier: String): String {
        val digestBytes = sha256(input = verifier)
        return Base64.UrlSafe.encode(source = digestBytes).trimEnd('=')
    }

    private fun sha256(input: String): ByteArray =
        input.encodeUtf8().sha256().toByteArray()
}