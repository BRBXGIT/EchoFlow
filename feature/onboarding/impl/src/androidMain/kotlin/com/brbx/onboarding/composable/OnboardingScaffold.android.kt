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
import com.brbx.onboarding.model.AndroidAuth
import com.brbx.onboarding.model.AndroidPage
import com.brbx.onboarding.model.OnboardingIntent
import com.brbx.onboarding.model.Special
import com.brbx.onboarding.view_model.OnboardingViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal actual fun OnboardingScaffold() {
    val viewModel = koinViewModel<OnboardingViewModel<AndroidPage>>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val dispatchIntent = viewModel::dispatchIntent

    HandleOnResume(dispatchIntent)

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { granted -> if (granted) dispatchIntent(OnboardingIntent.RefreshPages) }
    OnboardingScaffoldInternal(
        state = state,
        onPageAction = { page -> handlePageAction(page, launcher, context) },
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
    page: AndroidPage,
    launcher: ActivityResultLauncher<String>,
    context: Context,
) {
    if (page is AndroidAuth) handleAuth() else
        handlePermissions(page, launcher, context)
}

private fun handleAuth() {
    TODO()
}

private fun handlePermissions(
    page: AndroidPage,
    launcher: ActivityResultLauncher<String>,
    context: Context,
) {
    page.permission?.let {
        if (page is Special) page.ask(context) else {
            launcher.launch(input = page.permission!!)
        }
    }
}