package com.brbx.preferences.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.brbx.core.preferences.impl.TopLevelConfig
import com.brbx.preferences.StoreQualifier
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.dsl.module

private const val storeFileExt = "preferences_pb"

private val authStoreQualifier = StoreQualifier.Auth

internal actual val datastoreModule: Module = module {
    single(qualifier = authStoreQualifier) {
        createStore(authStoreQualifier)
    }
}

private val appDataDir by lazy {
    val localAppData = System.getenv("LOCALAPPDATA")
    "$localAppData\\${TopLevelConfig.appName}".toPath()
}

private fun createStore(qualifier: StoreQualifier): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath {
        appDataDir / "${qualifier.storeName}.$storeFileExt"
    }