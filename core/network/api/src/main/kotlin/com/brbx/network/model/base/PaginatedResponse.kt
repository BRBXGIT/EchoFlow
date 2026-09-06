package com.brbx.network.model.base

interface PaginatedResponse<out T : Any> {
    val collection: List<T>
    val nextHref: String? get() = null
    val futureHref: String? get() = null
}
