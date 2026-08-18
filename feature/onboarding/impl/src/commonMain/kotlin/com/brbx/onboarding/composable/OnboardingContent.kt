package com.brbx.onboarding.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.brbx.debug.compose.EchoFlowPreview
import com.brbx.design_system.theme.mDimens
import com.brbx.onboarding.view_model.onboarding_page.OnboardingPage
import com.brbx.onboarding.view_model.OnboardingState
import kotlinx.coroutines.launch

@Composable
internal fun OnboardingContent(
    state: OnboardingState,
    onOnboardingAction: (page: OnboardingPage) -> Unit,
    modifier: Modifier = Modifier,
) =
    SpacedColumn(modifier) {
        val pagerState = rememberPagerState { state.pageCount }
        val currentPage = pagerState.currentPage
        DotsRow(
            pageCount = state.pageCount,
            currentPage = currentPage,
            modifier = Modifier.padding(
                top = mDimens.micro8,
                start = mDimens.micro8,
                end = mDimens.micro8,
            )
        )

        val animationScope = rememberCoroutineScope()
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            val onboardingPage = remember(key1 = page) { state.pages[page] }
            OnboardingPage(
                page = onboardingPage,
                onSkip = {
                    animationScope.launch { pagerState.animateScrollToPage(currentPage + 1) }
                },
                onAction = { onOnboardingAction(onboardingPage) },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        bottom = mDimens.micro8,
                        start = mDimens.micro8,
                        end = mDimens.micro8,
                    )
            )
        }
    }

@EchoFlowPreview
@Composable
private fun OnboardingContentPreview() =
    OnboardingContent(state = OnboardingState(), onOnboardingAction = {}, modifier = Modifier.fillMaxSize())