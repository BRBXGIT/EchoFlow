package com.brbx.domain.model.util

import com.brbx.domain.model.enums.RequestException

sealed interface RequestResult<out T> {
    @JvmInline value class Success<out T>(val value: T) : RequestResult<T>

    @JvmInline value class Exception(val value: RequestException) : RequestResult<Nothing>
}

inline infix fun <T> RequestResult<T>.onSuccess(
    action: (T) -> Unit
): RequestResult<T> {
    if (this is RequestResult.Success) {
        action(this.value)
    }
    return this
}

inline infix fun <T> RequestResult<T>.onException(
    action: (RequestException) -> Unit
): RequestResult<T> {
    if (this is RequestResult.Exception) {
        action(this.value)
    }
    return this
}

fun <T> success(value: T): RequestResult<T> = RequestResult.Success(value)
fun failure(exception: RequestException): RequestResult<Nothing> = RequestResult.Exception(exception)

fun <T> T.asSuccess(): RequestResult<T> = RequestResult.Success(value = this)
fun RequestException.asFailure(): RequestResult<Nothing> = RequestResult.Exception(this)

inline infix fun <T, R> RequestResult<T>.map(transform: (T) -> R): RequestResult<R> {
    return when (this) {
        is RequestResult.Success -> RequestResult.Success(transform(this.value))
        is RequestResult.Exception -> this
    }
}

inline infix fun <T, R> RequestResult<T>.flatMap(transform: (T) -> RequestResult<R>): RequestResult<R> {
    return when (this) {
        is RequestResult.Success -> transform(this.value)
        is RequestResult.Exception -> this
    }
}

inline fun <T, R> RequestResult<T>.fold(
    onSuccess: (T) -> R,
    onException: (RequestException) -> R
): R {
    return when (this) {
        is RequestResult.Success -> onSuccess(this.value)
        is RequestResult.Exception -> onException(this.value)
    }
}