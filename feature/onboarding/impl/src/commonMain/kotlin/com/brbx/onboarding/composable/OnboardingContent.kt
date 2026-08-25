package com.brbx.onboarding.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.brbx.design_system.theme.mDimens
import com.brbx.onboarding.page_source.OnboardingPage
import com.brbx.onboarding.utils.scrollToNext
import kotlinx.coroutines.launch

@Composable
internal fun OnboardingContent(
    pagerState: PagerState,
    pages: List<OnboardingPage>,
    onAction: (page: OnboardingPage) -> Unit,
    modifier: Modifier = Modifier,
) =
    SpacedColumn(modifier) {
        val currentPage = pagerState.currentPage
        DotsRow(
            pageCount = pages.size,
            currentPage = currentPage,
            modifier = Modifier.padding(
                top = mDimens.micro8,
                start = mDimens.micro8,
                end = mDimens.micro8,
            ),
        )

        val animationScope = rememberCoroutineScope()
        OnboardingPager(
            pagerState = pagerState,
            modifier = Modifier.weight(1f),
        ) { page ->
            OnboardingPage(
                page = pages[page],
                onSkip = { animationScope.launch { pagerState.scrollToNext() } },
                onAction = onAction,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        bottom = mDimens.micro8,
                        start = mDimens.micro8,
                        end = mDimens.micro8,
                    ),
            )
        }
    }