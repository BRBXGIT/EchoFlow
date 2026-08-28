package com.brbx.data.di

import com.brbx.data.repository.UserAuthRepository
import com.brbx.data.repository.UserAuthRepositoryImpl
import com.brbx.preferences.getAuthPrefs
import org.koin.dsl.module

internal val repositoryModule = module {
    single<UserAuthRepository> {
        UserAuthRepositoryImpl(authPrefs = getAuthPrefs())
    }
}