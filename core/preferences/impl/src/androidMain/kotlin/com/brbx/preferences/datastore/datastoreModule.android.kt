package com.brbx.preferences.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.brbx.preferences.StoreQualifier
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

private val authStoreQualifier = StoreQualifier.Auth
private val Context.authStore by preferencesDataStore(authStoreQualifier.storeName)

internal actual val datastoreModule: Module = module {
    single(qualifier = authStoreQualifier) {
        androidContext().authStore
    }
}