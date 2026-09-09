package com.brbx.home.model

import androidx.compose.runtime.Immutable
import com.brbx.domain.model.common.Track
import com.brbx.feature_common.model.LoadingCollection
import com.brbx.feature_common.model.UiPaginationState

@Immutable
internal data class HomeState(
    val recentlyListened: LoadingCollection<Track> = LoadingCollection(),
    val relatedToRecently: UiPaginationState<Track> = UiPaginationState(),
)