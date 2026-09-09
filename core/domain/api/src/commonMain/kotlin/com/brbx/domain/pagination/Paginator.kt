package com.brbx.domain.pagination

import kotlinx.coroutines.flow.StateFlow

// Implemented in data used in viewModel
interface Paginator<T> {
    val state: StateFlow<PaginationState<T>>

    suspend fun loadNext()

    suspend fun refresh()
}