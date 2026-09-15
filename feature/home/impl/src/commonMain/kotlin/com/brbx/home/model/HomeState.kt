package com.brbx.home.model

import androidx.compose.runtime.Immutable
import com.brbx.design_system.components.components.TrackItem
import com.brbx.feature_common.model.LoadingCollection
import com.brbx.feature_common.model.UiPaginationState
import kotlinx.collections.immutable.toPersistentList

@Immutable
internal data class HomeState(
    val recentlyListened: LoadingCollection<TrackItem> = LoadingCollection(isLoading = true),
    val relatedToRecently: UiPaginationState<TrackItem> = UiPaginationState(isLoading = true),
    val todayMixVisible: Boolean = false,
    val recentSheetsVisible: Boolean = false,
) {
    val recentlyPosters get() = recentlyListened
        .collection
        .map { track -> track.poster }
        .take(n = 3)
        .toPersistentList()
}