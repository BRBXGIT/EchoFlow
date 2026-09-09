package com.brbx.feature_common.model

import androidx.compose.runtime.Immutable
import com.brbx.domain.model.common.Track
import com.brbx.domain.model.enums.RequestException
import com.brbx.domain.pagination.PaginationState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Immutable
data class UiPaginationState<T>(
    val items: ImmutableList<T> = persistentListOf(),
    val exception: RequestException? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val endReached: Boolean = false
) {
    val isLoadingOrRefreshing get() = isLoading || isRefreshing

    fun itemsSnapshot(n: Int): ImmutableList<T> =
        items.take(n).toPersistentList()
}

fun <T> PaginationState<T>.toUi() =
    UiPaginationState(
        items = items.toPersistentList(),
        exception = exception,
        isLoading = isLoading,
        isRefreshing = isRefreshing,
        endReached = endReached,
    )