package com.brbx.data.di

import com.brbx.data.inversion.TokensInteractorImpl
import com.brbx.network.inversion.TokensInteractor
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val inversionModule = module {
    singleOf(constructor = ::TokensInteractorImpl) { bind<TokensInteractor>() }
}