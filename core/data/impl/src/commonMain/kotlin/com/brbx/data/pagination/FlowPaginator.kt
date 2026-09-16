package com.brbx.data.pagination

import com.brbx.domain.model.common.RequestResult
import com.brbx.domain.model.common.onException
import com.brbx.domain.model.common.onSuccess
import com.brbx.domain.pagination.PaginationState
import com.brbx.domain.pagination.Paginator
import com.brbx.network.model.base.PaginatedDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class FlowPaginator<Dto, Domain>(
    private val call: suspend (key: String?) -> RequestResult<PaginatedDto<Dto>>,
    private val mapper: (Dto) -> Domain,
) : Paginator<Domain> {

    private var key: String? = null
    private val _state = MutableStateFlow(value = PaginationState<Domain>())
    private val currentState get() = _state.value

    override val state = _state.asStateFlow()

    override suspend fun loadNext() {
        if (currentState.endReached) return
        load(isRefresh = false)
    }

    override suspend fun refresh() {
        load(isRefresh = true)
    }

    private suspend fun load(isRefresh: Boolean) {
        if (currentState.isLoading || currentState.isRefreshing) return

        if (isRefresh) key = null

        mutate {
            copy(
                isRefreshing = isRefresh,
                isLoading = !isRefresh,
                exception = null
            )
        }

        call(key) onSuccess { paged ->
            key = paged.nextHref
            val newItems = paged.collection.map(transform = mapper)

            mutate {
                copy(
                    isRefreshing = false,
                    isLoading = false,
                    items = if (isRefresh) newItems else items + newItems,
                    endReached = paged.nextHref == null
                )
            }
        } onException { e ->
            mutate {
                copy(
                    isRefreshing = false,
                    isLoading = false,
                    exception = e
                )
            }
        }
    }

    private fun mutate(function: PaginationState<Domain>.() -> PaginationState<Domain>) =
        _state.update(function)
}