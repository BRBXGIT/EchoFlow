package com.brbx.common_app

import com.brbx.core_common.coreCommonModule
import com.brbx.data.dataModule
import com.brbx.domain.domainModule
import com.brbx.onboarding.onboardingModule
import com.brbx.preferences.preferencesModule
import org.koin.dsl.module

val projectModules = module {
    includes(
        coreCommonModule,
        dataModule,
        domainModule,
        preferencesModule,
        onboardingModule,
    )
}