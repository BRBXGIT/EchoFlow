package com.brbx.playlist.model

import androidx.compose.runtime.Immutable
import com.brbx.design_system.components.components.TrackItem
import com.brbx.feature_common.model.LoadingCollection
import com.brbx.feature_common.utils.CommonText
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
internal data class PlaylistState(
    val tracks: ImmutableList<TrackItem> = persistentListOf(),
    val title: CommonText = CommonText.Raw(value = ""),
    val isLoading: Boolean = false,
)