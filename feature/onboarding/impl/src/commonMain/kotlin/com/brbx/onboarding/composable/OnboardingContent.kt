package com.brbx.onboarding.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.brbx.debug.compose.EchoFlowPreview
import com.brbx.design_system.theme.mDimens
import com.brbx.onboarding.view_model.OnboardingState
import com.brbx.onboarding.view_model.onboarding_page.OnboardingAction
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
internal fun OnboardingContent(
    state: OnboardingState,
    onAction: (action: OnboardingAction) -> Unit,
    modifier: Modifier = Modifier,
    rendererRegistry: OnboardingRendererRegistry = koinInject(),
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
            ),
        )

        val animationScope = rememberCoroutineScope()
        OnboardingPager(
            pagerState = pagerState,
            modifier = Modifier.weight(1f),
        ) { page ->
            val onboardingPage = remember(key1 = page) { state.pages[page] }
            rendererRegistry.Render(
                page = onboardingPage,
                onSkip = {
                    animationScope.launch { pagerState.animateScrollToPage(currentPage + 1) }
                },
                onAction = { onboardingPage.action?.let(block = onAction) },
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
    OnboardingContent(state = OnboardingState(), onAction = {}, modifier = Modifier.fillMaxSize())