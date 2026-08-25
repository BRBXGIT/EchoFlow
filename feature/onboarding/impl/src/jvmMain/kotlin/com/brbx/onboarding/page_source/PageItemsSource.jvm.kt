package com.brbx.onboarding.page_source

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Stable
private class JvmPageItemsSource : PageItemsSource {
    override fun createPages(): Flow<List<OnboardingPage>> =
        flowOf(value = createPagesInternal())

    override fun refreshPages() { /* Nothing */ }
}

@Composable
internal actual fun rememberPageItemsSource(): PageItemsSource =
    remember { JvmPageItemsSource() }