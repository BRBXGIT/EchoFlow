package com.brbx.onboarding.page_source

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import kotlinx.coroutines.flow.Flow

@Immutable
internal interface PageItemsSource {
    fun createPages(): Flow<List<OnboardingPage>>

    fun refreshPages()
}

@Composable
internal expect fun rememberPageItemsSource(): PageItemsSource