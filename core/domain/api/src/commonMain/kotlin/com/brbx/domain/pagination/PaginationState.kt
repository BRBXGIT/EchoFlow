package com.brbx.domain.pagination

import com.brbx.domain.model.enums.RequestException

data class PaginationState<T>(
    val items: List<T> = emptyList(),
    val exception: RequestException? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val endReached: Boolean = false
)