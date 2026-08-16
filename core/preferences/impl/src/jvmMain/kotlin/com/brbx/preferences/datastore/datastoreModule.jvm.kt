package com.brbx.preferences.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
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

// TODO Remove relative path
private fun createStore(qualifier: StoreQualifier): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath { "${qualifier.storeName}.$storeFileExt".toPath() }