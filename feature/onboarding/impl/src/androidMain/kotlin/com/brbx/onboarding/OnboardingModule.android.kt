package com.brbx.onboarding

import com.brbx.onboarding.view_model.onboarding_page.AndroidPermissionChecker
import com.brbx.onboarding.view_model.onboarding_page.PermissionChecker
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual val platformOnboardingModule: Module = module {
    single<PermissionChecker> { AndroidPermissionChecker(context = androidContext()) }
}
