package com.brbx.onboarding.di

import com.brbx.feature_common.view_model.mviDelegateOf
import com.brbx.onboarding.model.DesktopPagePayload
import com.brbx.onboarding.view_model.AuthLinkHandler
import com.brbx.onboarding.view_model.JvmAuthLinkHandler
import com.brbx.onboarding.view_model.JvmPagesSource
import com.brbx.onboarding.view_model.PagesSource
import org.koin.dsl.bind
import org.koin.dsl.module

internal actual val viewModelModule = module {
    mviDelegateOf(::JvmPagesSource) bind PagesSource::class
    mviDelegateOf(::JvmAuthLinkHandler) bind AuthLinkHandler::class

    viewModelModuleInternal<DesktopPagePayload>()
}
