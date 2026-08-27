package com.brbx.onboarding.model

import android.content.Context
import androidx.compose.runtime.Immutable

@Immutable
internal interface AndroidPage : OnboardingPage {
    val permission: String? get() = null

    fun withEnabledAction(isEnabled: Boolean): AndroidPage = this
}

@Immutable
internal interface Special : AndroidPage {
    fun isGranted(context: Context): Boolean
    fun ask(context: Context)
}