package com.brbx.onboarding.di

import com.brbx.feature_common.view_model.mviDelegateOf
import com.brbx.onboarding.model.AndroidPagePayload
import com.brbx.onboarding.view_model.AndroidAuthLinkHandler
import com.brbx.onboarding.view_model.AndroidPagesSource
import com.brbx.onboarding.view_model.AuthLinkHandler
import com.brbx.onboarding.view_model.PagesSource
import org.koin.dsl.bind
import org.koin.dsl.module

internal actual val viewModelModule = module {
    mviDelegateOf(::AndroidPagesSource) bind PagesSource::class
    mviDelegateOf(::AndroidAuthLinkHandler) bind AuthLinkHandler::class

    viewModelModuleInternal<AndroidPagePayload>()
}
