package com.brbx.onboarding.view_model.delegate

import org.koin.dsl.module

internal val delegatesModule = module {
    factory<PageDelegate> { params ->
        PageDelegateImpl(scope = params.get())
    }
}