package com.brbx.onboarding.composable

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brbx.onboarding.model.AndroidAuthPayload
import com.brbx.onboarding.model.AndroidPagePayload
import com.brbx.onboarding.model.OnboardingIntent
import com.brbx.onboarding.model.OnboardingPage
import com.brbx.onboarding.model.SpecialAndroidPagePayload
import com.brbx.onboarding.view_model.OnboardingViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal actual fun OnboardingScaffold(deeplink: String?) {
    val viewModel = koinViewModel<OnboardingViewModel<AndroidPagePayload>>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val dispatchIntent = viewModel::dispatchIntent

    HandleOnResume(dispatchIntent)
    HandleDeeplink(deeplink, dispatchIntent)

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { granted -> if (granted) dispatchIntent(OnboardingIntent.RefreshPages) }
    OnboardingScaffoldInternal(
        state = state,
        effects = viewModel.effects,
        onPageAction = { page -> handlePageAction(page, launcher, context, dispatchIntent) },
    )
}

@Composable
private fun HandleOnResume(dispatchIntent: (OnboardingIntent) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                dispatchIntent(OnboardingIntent.RefreshPages)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

private fun handlePageAction(
    page: OnboardingPage<AndroidPagePayload>,
    launcher: ActivityResultLauncher<String>,
    context: Context,
    dispatchIntent: (OnboardingIntent) -> Unit,
) =
    if (page.payload is AndroidAuthPayload) handleAuth(dispatchIntent) else
        handlePermissions(page.payload, launcher, context)

private fun handleAuth(dispatchIntent: (OnboardingIntent) -> Unit) =
    dispatchIntent(OnboardingIntent.OpenAuthLink)

private fun handlePermissions(
    payload: AndroidPagePayload,
    launcher: ActivityResultLauncher<String>,
    context: Context,
) =
    payload.permission?.let { permission ->
        if (payload is SpecialAndroidPagePayload) payload.ask(context) else {
            launcher.launch(input = permission)
        }
    }
