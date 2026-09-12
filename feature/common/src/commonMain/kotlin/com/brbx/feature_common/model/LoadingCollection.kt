package com.brbx.feature_common.model

import androidx.compose.runtime.Immutable
import com.brbx.domain.model.base.ItemsCollection
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Immutable
data class LoadingCollection<T>(
    val collection: ImmutableList<T> = persistentListOf(),
    val isLoading: Boolean = false,
) {
    fun collectionSnapshot(n: Int): ImmutableList<T> =
        collection.take(n).toPersistentList()
}

fun <T> ItemsCollection<T>.toUi() =
    LoadingCollection(
        collection = collection.toPersistentList(),
    )
