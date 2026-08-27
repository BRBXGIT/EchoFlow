package com.brbx.onboarding.utils

import androidx.compose.foundation.pager.PagerState

internal suspend fun PagerState.scrollToNext() {
    animateScrollToPage(currentPage + 1)
}

internal suspend fun PagerState.scrollToPrevious() {
    animateScrollToPage(currentPage - 1)
}