package com.brbx.feature_common.model

import androidx.compose.runtime.Immutable
import com.brbx.domain.model.base.ItemsCollection

@Immutable
data class LoadingCollection<T>(
    val collection: List<T> = emptyList(),
    val isLoading: Boolean = false,
)

fun <T> ItemsCollection<T>.toUi() =
    LoadingCollection(collection = collection)
