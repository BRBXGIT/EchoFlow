package com.brbx.common_app

import com.brbx.core_common.coreCommonModule
import com.brbx.data.di.dataModule
import com.brbx.domain.di.domainModule
import com.brbx.onboarding.di.onboardingModule
import com.brbx.preferences.preferencesModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

fun setupKoin(
    block: KoinApplication.() -> Unit = {},
) {
    startKoin {
        block()
        modules(
            coreCommonModule,
            dataModule,
            domainModule,
            preferencesModule,
            onboardingModule,
        )
    }
}