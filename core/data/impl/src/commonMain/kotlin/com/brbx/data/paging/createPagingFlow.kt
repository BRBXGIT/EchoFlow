package com.brbx.data.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow

private const val DefaultPageSize = 50
private const val DefaultPrefetchDistance = 10

internal inline fun <Key : Any, Value : Any> createPagingFlow(
    pageSize: Int = DefaultPageSize,
    prefetchDistance: Int = DefaultPrefetchDistance,
    enablePlaceholders: Boolean = false,
    initialLoadSize: Int = pageSize,
    crossinline pagingSourceFactory: () -> PagingSource<Key, Value>,
): Flow<PagingData<Value>> =
    Pager(
        config = PagingConfig(
            pageSize = pageSize,
            prefetchDistance = prefetchDistance,
            enablePlaceholders = enablePlaceholders,
            initialLoadSize = initialLoadSize,
        ),
        pagingSourceFactory = { pagingSourceFactory() },
    ).flow