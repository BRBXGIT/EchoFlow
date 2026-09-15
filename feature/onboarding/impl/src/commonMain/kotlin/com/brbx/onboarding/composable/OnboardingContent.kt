package com.brbx.onboarding.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.brbx.debug.compose.EchoFlowScreenPreview
import com.brbx.design_system.theme.mDimens
import com.brbx.onboarding.model.OnboardingPage
import com.brbx.onboarding.model.createGreetingPage
import com.brbx.onboarding.utils.scrollToNext
import kotlinx.coroutines.launch

@Composable
internal fun <T> OnboardingContent(
    pagerState: PagerState,
    pages: List<OnboardingPage<T>>,
    onAction: (OnboardingPage<T>) -> Unit,
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
            val current = pages[page]
            OnboardingPage(
                page = current,
                onSkip = { animationScope.launch { pagerState.scrollToNext() } },
                onAction = { onAction(current) },
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

@Composable
@EchoFlowScreenPreview
private fun OnboardingContentPreview() {
    val pages = listOf(createGreetingPage(payload = 0))
    val pagerState = rememberPagerState { pages.size }
    OnboardingContent(
        pagerState = pagerState,
        pages = pages,
        onAction = {},
    )
}


