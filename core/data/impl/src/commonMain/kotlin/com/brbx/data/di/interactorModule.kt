package com.brbx.data.di

import com.brbx.data.interactor.UserAuthInteractor
import com.brbx.data.interactor.UserAuthInteractorImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val interactorModule = module {
    singleOf(constructor = ::UserAuthInteractorImpl) { bind<UserAuthInteractor>() }
}