package com.brbx.onboarding.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brbx.design_system.components.utils.rememberSnackbarHost
import com.brbx.design_system.theme.mColors
import com.brbx.feature_common.composable.HandleEchoFlowEffects
import com.brbx.feature_common.view_model.EchoFlowEffect
import com.brbx.onboarding.view_model.OnboardingEffect
import com.brbx.onboarding.view_model.OnboardingIntent
import com.brbx.onboarding.view_model.OnboardingViewModel
import com.brbx.onboarding.view_model.onboarding_page.OnboardingAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun OnboardingScaffold(
    viewModel: OnboardingViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val postScreenEffect = viewModel::postScreenEffect

    val snackbarHost = rememberSnackbarHost()
    val pagerState = rememberPagerState { state.pageCount }
    HandleEchoFlowEffects(viewModel.effects, snackbarHost)
    HandleScreenEffects(
        screenEffects = viewModel.screenEffects,
        postEffect = viewModel::postEffect,
        dispatchIntent = viewModel::dispatchIntent,
        pagerState = pagerState,
    )

    Scaffold(
        bottomBar = { OnboardingNavBar(postScreenEffect) },
        snackbarHost = { SnackbarHost(hostState = snackbarHost) },
        modifier = Modifier
            .fillMaxSize()
            .background(color = mColors.background)
    ) { innerPadding ->
        OnboardingContent(
            onAction = { action ->
                val effect = OnboardingEffect.HandleAction(action)
                viewModel.postScreenEffect(effect)
            },
            postScreenEffect = postScreenEffect,
            state = state,
            pagerState = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
        )
    }
}

@Composable
internal expect fun HandleScreenEffects(
    screenEffects: SharedFlow<OnboardingEffect>,
    pagerState: PagerState,
    postEffect: (EchoFlowEffect) -> Unit,
    dispatchIntent: (OnboardingIntent) -> Unit,
)

@Composable
internal inline fun HandleScreenEffectsInternal(
    screenEffects: SharedFlow<OnboardingEffect>,
    pagerState: PagerState,
    crossinline handleAction: (action: OnboardingAction) -> Unit,
) =
    LaunchedEffect(key1 = screenEffects) {
        screenEffects.collect { effect ->
            when (effect) {
                is OnboardingEffect.HandleAction -> handleAction(effect.action)
                is OnboardingEffect.NextPage -> pagerState.plusOrMinusPage(next = true)
                is OnboardingEffect.PreviousPage -> pagerState.plusOrMinusPage(next = false)
            }
        }
    }

context(coroutineScope: CoroutineScope)
private fun PagerState.plusOrMinusPage(next: Boolean) {
    coroutineScope.launch {
        if (next) {
            animateScrollToPage(currentPage + 1)
        } else animateScrollToPage(currentPage - 1)
    }
}
