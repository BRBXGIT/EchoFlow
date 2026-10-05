package com.brbx.data.handler

import com.brbx.data.common.toRequestException
import com.brbx.domain.model.common.RequestResult
import com.brbx.domain.model.enums.RequestException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.http.HttpStatusCode
import kotlinx.io.IOException
import kotlin.coroutines.cancellation.CancellationException

internal interface NetworkResponseHandler {
    suspend fun <T> handle(call: suspend () -> T): RequestResult<T>
}

internal class NetworkResponseHandlerImpl : NetworkResponseHandler {
    override suspend fun <T> handle(call: suspend () -> T): RequestResult<T> =
        try {
            val result = call()
            RequestResult.Success(value = result)
        } catch (e: ClientRequestException) {
            val requestException = e.response.status.value.toRequestException()
            RequestResult.Exception(requestException)
        } catch (_: ServerResponseException) {
            RequestResult.Exception(RequestException.ServerError)
        } catch (_: HttpRequestTimeoutException) {
            RequestResult.Exception(RequestException.RequestTimeout)
        } catch (_: IOException) {
            RequestResult.Exception(RequestException.Internet)
        } catch (_: Exception) {
            RequestResult.Exception(RequestException.Unknown)
        } catch (e: CancellationException) {
            throw e
        }
}