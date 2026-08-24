package com.brbx.common_app

import com.brbx.core_common.coreCommonModule
import com.brbx.data.dataModule
import com.brbx.domain.domainModule
import com.brbx.onboarding.onboardingModule
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