package com.brbx.home.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.brbx.design_system.theme.mDimens
import com.brbx.domain.model.common.Track

@Composable
internal fun HomeContent(
    relatedToRecent: LazyPagingItems<Track>?,
    modifier: Modifier = Modifier,
) =
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(mDimens.micro8),
        contentPadding = PaddingValues(vertical = mDimens.micro8),
        modifier = modifier,
    ) {
        relatedToRecentTracks(relatedToRecent)
    }