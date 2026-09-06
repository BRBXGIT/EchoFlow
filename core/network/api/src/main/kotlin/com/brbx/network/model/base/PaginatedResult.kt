package com.brbx.network.model.base

interface PaginatedResult<out T : Any> {
    val collection: List<T>
    val nextHref: String? get() = null
    val futureHref: String? get() = null
}