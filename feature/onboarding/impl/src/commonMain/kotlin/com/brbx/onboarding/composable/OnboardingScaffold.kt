package com.brbx.onboarding.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.brbx.design_system.theme.mColors
import com.brbx.onboarding.model.OnboardingPage
import com.brbx.onboarding.model.OnboardingState

@Composable
internal expect fun OnboardingScaffold()

@Composable
internal fun <T : OnboardingPage> OnboardingScaffoldInternal(
    state: OnboardingState<T>,
    onPageAction: (T) -> Unit,
) {
    val pages = state.pages
    val pagerState = rememberPagerState { pages.size }
    Scaffold(
        bottomBar = { OnboardingNavBar(pagerState) },
        modifier = Modifier
            .fillMaxSize()
            .background(mColors.background)
    ) { innerPadding ->
        OnboardingContent(
            pagerState = pagerState,
            pages = pages,
            onAction = onPageAction,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
        )
    }
}