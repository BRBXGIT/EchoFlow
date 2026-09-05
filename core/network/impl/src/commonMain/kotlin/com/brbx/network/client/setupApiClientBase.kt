package com.brbx.network.client

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.util.concurrent.TimeUnit

private const val UserAgent = "Mozilla/5.0 (Linux; Android 10; K) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36"
private const val DefaultTimeout = 30L

private val networkJson = Json {
    ignoreUnknownKeys = true
    prettyPrint = true
    isLenient = true
}

internal fun setupApiClient(
    baseUrl: String,
    connectTimeout: Long = DefaultTimeout,
    readTimeout: Long = DefaultTimeout,
    writeTimeout: Long = DefaultTimeout,
    block: HttpClientConfig<OkHttpConfig>.() -> Unit = {},
): Lazy<HttpClient> =
    lazy {
        HttpClient(engineFactory = OkHttp) {
            expectSuccess = true
            engine {
                config {
                    connectTimeout(connectTimeout, unit = TimeUnit.SECONDS)
                    readTimeout(readTimeout, unit = TimeUnit.SECONDS)
                    writeTimeout(writeTimeout, unit = TimeUnit.SECONDS)
                }
            }
            install(plugin = DefaultRequest) {
                url(urlString = baseUrl)
                header(HttpHeaders.UserAgent, UserAgent)
            }
            install(plugin = ContentNegotiation) {
                json(json = networkJson)
            }
            install(plugin = Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.BODY // TODO make NONE in release
            }
            block()
        }
    }