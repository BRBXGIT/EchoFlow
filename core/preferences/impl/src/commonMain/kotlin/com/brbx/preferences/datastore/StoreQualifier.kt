package com.brbx.preferences.datastore

import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue

internal enum class StoreQualifier(val storeName: String) : Qualifier {
    Auth(storeName = "echo_flow_auth_prefs");

    override val value: QualifierValue = this.name
}