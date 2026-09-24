package com.brbx.playlist.model

import androidx.compose.runtime.Immutable
import com.brbx.design_system.components.components.TrackItem
import com.brbx.feature_common.model.UiPaginationState
import com.brbx.feature_common.utils.CommonText

@Immutable
internal data class PlaylistState(
    val tracks: UiPaginationState<TrackItem> = UiPaginationState(),
    val title: CommonText = CommonText.Raw(value = ""),
    val isLoading: Boolean = false,
)