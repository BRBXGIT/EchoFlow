package com.brbx.onboarding.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.brbx.design_system.theme.mColors
import com.brbx.onboarding.page_source.OnboardingPage
import com.brbx.onboarding.page_source.PageItemsSource
import com.brbx.onboarding.page_source.rememberPageItemsSource

@Composable
internal fun OnboardingScaffoldInternal(
    source: PageItemsSource,
    onPageAction: (page: OnboardingPage) -> Unit,
) {
    val pages by source.createPages().collectAsState(initial = emptyList())
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

@Composable
internal expect fun OnboardingScaffold()