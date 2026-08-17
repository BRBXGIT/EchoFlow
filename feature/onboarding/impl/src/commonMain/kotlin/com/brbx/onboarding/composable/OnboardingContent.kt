package com.brbx.onboarding.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.brbx.debug.compose.EchoFlowPreview
import com.brbx.design_system.theme.mDimens
import com.brbx.onboarding.view_model.model.OnboardingState
import kotlinx.coroutines.launch

@Composable
internal fun OnboardingContent(
    state: OnboardingState,
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
        Pager(
            state = pagerState,
            modifier = Modifier.weight(1f),
        ) { page ->
            OnboardingPage(
                page = state.pages[page],
                onSkip = {
                    animationScope.launch { pagerState.animateScrollToPage(currentPage + 1) }
                },
                onAction = {},
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
    OnboardingContent(state = OnboardingState(), modifier = Modifier.fillMaxSize())