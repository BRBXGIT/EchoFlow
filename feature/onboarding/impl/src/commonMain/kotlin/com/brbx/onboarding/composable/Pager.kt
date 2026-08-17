package com.brbx.onboarding.composable

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun Pager(
    state: PagerState,
    modifier: Modifier = Modifier,
    content: @Composable PagerScope.(page: Int) -> Unit,
) {
    HorizontalPager(
        modifier = modifier,
        state = state,
    ) { page ->
        content(page)
    }
}
