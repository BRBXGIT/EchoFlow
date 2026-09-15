package com.brbx.onboarding.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.brbx.debug.compose.EchoFlowScreenPreview
import com.brbx.design_system.theme.mColors
import com.brbx.feature_common.composable.HandleEchoFlowEffects
import com.brbx.feature_common.view_model.EchoFlowEffect
import com.brbx.onboarding.model.OnboardingIntent
import com.brbx.onboarding.model.OnboardingPage
import com.brbx.onboarding.model.OnboardingState
import com.brbx.onboarding.model.createGreetingPage
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

@Composable
internal expect fun OnboardingScaffold(deeplink: String?)

@Composable
internal fun <T> OnboardingScaffoldInternal(
    state: OnboardingState<T>,
    effects: SharedFlow<EchoFlowEffect>,
    onPageAction: (OnboardingPage<T>) -> Unit,
) {
    val snackbarHost = remember { SnackbarHostState() }
    HandleEchoFlowEffects(effects, snackbarHost)

    val pages = state.pages
    val pagerState = rememberPagerState { pages.size }
    LoadingBox(
        loading = state.loading,
        modifier = Modifier.fillMaxSize(),
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHost) },
            bottomBar = { OnboardingNavBar(pagerState) },
            modifier = Modifier
                .fillMaxSize()
                .background(color = mColors.background)
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
}

@Composable
internal fun HandleDeeplink(
    deeplink: String?,
    dispatchIntent: (OnboardingIntent) -> Unit,
) =
    LaunchedEffect(key1 = deeplink) {
        deeplink?.let { dispatchIntent(OnboardingIntent.Authenticate(deeplink)) }
    }

@Composable
@EchoFlowScreenPreview
private fun OnboardingScaffoldInternalPreview() {
    val state = OnboardingState(
        pages = listOf(createGreetingPage(payload = 0)),
        loading = false,
    )
    OnboardingScaffoldInternal(
        state = state,
        effects = MutableSharedFlow(),
        onPageAction = {},
    )
}


