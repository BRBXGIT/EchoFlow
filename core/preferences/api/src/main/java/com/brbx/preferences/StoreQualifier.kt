package com.brbx.preferences

import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue
import org.koin.core.scope.Scope

enum class StoreQualifier(val storeName: String) : Qualifier {
    Auth(storeName = "echo_flow_auth_prefs");

    override val value: QualifierValue = this.name
}

fun Scope.getAuthStore(): AuthPrefsManager = get(qualifier = StoreQualifier.Auth)