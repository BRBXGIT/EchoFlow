package com.brbx.onboarding.view_model

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.brbx.mvi_core.helpers.reduce
import com.brbx.onboarding.model.AndroidAuth
import com.brbx.onboarding.model.AndroidGreeting
import com.brbx.onboarding.model.AndroidPage
import com.brbx.onboarding.model.BatteryOptimization
import com.brbx.onboarding.model.Notifications
import com.brbx.onboarding.model.OnboardingIntent
import com.brbx.onboarding.model.Special

internal class AndroidPagesDelegate(
    override val scope: OnboardingMviScope<AndroidPage>,
    private val context: Context, // Application context in viewModel antipattern maybe will be rewritten
) : PagesDelegate<AndroidPage> {
    override fun invoke(intent: OnboardingIntent.RefreshPages) =
        reduce { copy(pages = buildPages()) }

    private fun buildPages(): List<AndroidPage> =
        buildList {
            add(AndroidGreeting)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                add(Notifications().checkIsGranted())
            }
            add(BatteryOptimization().checkIsGranted())
            add(AndroidAuth)
        }

    private fun AndroidPage.checkIsGranted(): AndroidPage =
        permission?.let { permission ->
            val isGranted = if (this is Special) isGranted(context) else {
                ContextCompat.checkSelfPermission(context, permission) ==
                        PackageManager.PERMISSION_GRANTED
            }
            withEnabledAction(isEnabled = !isGranted)
        } ?: this
}