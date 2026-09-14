package com.brbx.feature_common.model

import com.brbx.design_system.components.components.TrackItem
import com.brbx.domain.model.base.ItemsCollection
import com.brbx.domain.model.common.Track
import com.brbx.domain.pagination.PaginationState

fun Track.toUi(): TrackItem =
    TrackItem(
        id = id,
        title = title,
        poster = highResArtworkUrl,
        artist = user?.name,
    )

fun PaginationState<Track>.toUi(): UiPaginationState<TrackItem> =
    toUi(itemsMapper = { it.toUi() })

fun ItemsCollection<Track>.toUi(): LoadingCollection<TrackItem> =
    toUi(itemsMapper = { it.toUi() })