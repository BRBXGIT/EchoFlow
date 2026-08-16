package com.brbx.preferences.manager

import com.brbx.preferences.AuthPrefsManager
import com.brbx.preferences.datastore.StoreQualifier
import org.koin.dsl.module

internal val managerModule = module {
    single<AuthPrefsManager> {
        AuthPrefsManagerImpl(store = get(qualifier = StoreQualifier.Auth))
    }
}