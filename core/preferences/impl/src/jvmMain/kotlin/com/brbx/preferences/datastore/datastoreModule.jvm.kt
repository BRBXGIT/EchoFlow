package com.brbx.preferences.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.brbx.core_common.TopLevelAppConfig
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

// TODO Move hardcoded EchoFlow to app config
private val appDataDir by lazy {
    val localAppData = System.getenv("LOCALAPPDATA")
    "$localAppData\\${TopLevelAppConfig.AppName}".toPath()
}

private fun createStore(qualifier: StoreQualifier): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath {
        appDataDir / "${qualifier.storeName}.$storeFileExt"
    }