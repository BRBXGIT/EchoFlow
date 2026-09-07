package com.brbx.home.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.brbx.domain.model.Track
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun HomeContent(
    recentlyPlayed: ImmutableList<Track>,
    modifier: Modifier = Modifier,
) {}