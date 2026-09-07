package com.brbx.domain.model.util

sealed class PagingException : Exception() {
    class Conflict : PagingException()
    class TooManyRequests : PagingException()
    class PayloadTooLarge : PagingException()
    class ServerError : PagingException()
    class Unauthorized : PagingException()
    class RequestTimeout : PagingException()
    class Internet : PagingException()
    class Unknown : PagingException()
    class CsrfAttack : PagingException()
}