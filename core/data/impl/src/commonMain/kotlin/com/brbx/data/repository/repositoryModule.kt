package com.brbx.data.repository

import com.brbx.preferences.getAuthStore
import org.koin.dsl.module

internal val repositoryModule = module {
    single<UserAuthRepository> {
        UserAuthRepositoryImpl(authPrefs = getAuthStore())
    }
}