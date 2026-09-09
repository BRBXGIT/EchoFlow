package com.brbx.feature_common.model

import androidx.compose.runtime.Immutable
import com.brbx.domain.model.enums.RequestException
import com.brbx.domain.pagination.PaginationState

@Immutable
data class UiPaginationState<T>(
    val items: List<T> = emptyList(),
    val exception: RequestException? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val endReached: Boolean = false
)

fun <T> PaginationState<T>.toUi() =
    UiPaginationState(
        items = items,
        exception = exception,
        isLoading = isLoading,
        isRefreshing = isRefreshing,
        endReached = endReached,
    )