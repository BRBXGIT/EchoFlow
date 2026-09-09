package com.brbx.onboarding.view_model

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.brbx.mvi_core.helpers.reduce
import com.brbx.onboarding.model.AndroidAuthPayload
import com.brbx.onboarding.model.AndroidGreetingPayload
import com.brbx.onboarding.model.AndroidPagePayload
import com.brbx.onboarding.model.OnboardingIntent
import com.brbx.onboarding.model.OnboardingPage
import com.brbx.onboarding.model.SpecialAndroidPagePayload
import com.brbx.onboarding.model.createAuthPage
import com.brbx.onboarding.model.createBatteryOptimizationPage
import com.brbx.onboarding.model.createGreetingPage
import com.brbx.onboarding.model.createNotificationsPage

internal class AndroidPagesSource(
    override val scope: OnboardingMviScope<AndroidPagePayload>,
    private val context: Context,
) : PagesSource<AndroidPagePayload> {
    override fun invoke(intent: OnboardingIntent.RefreshPages) =
        reduce { copy(pages = buildPages()) }

    private fun buildPages(): List<OnboardingPage<AndroidPagePayload>> =
        buildList {
            add(createGreetingPage(AndroidGreetingPayload))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                add(createNotificationsPage().checkIsGranted())
            }
            add(createBatteryOptimizationPage().checkIsGranted())
            add(createAuthPage(AndroidAuthPayload))
        }

    private fun <P : AndroidPagePayload> OnboardingPage<P>.checkIsGranted(): OnboardingPage<P> =
        payload.permission?.let { permission ->
            val isGranted = if (payload is SpecialAndroidPagePayload) payload.isGranted(context) else {
                ContextCompat.checkSelfPermission(context, permission) ==
                        PackageManager.PERMISSION_GRANTED
            }
            copy(action = action?.copy(enabled = !isGranted))
        } ?: this
}
