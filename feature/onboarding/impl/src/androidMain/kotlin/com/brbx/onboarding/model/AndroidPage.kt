package com.brbx.onboarding.model

import android.content.Context
import androidx.compose.runtime.Immutable

@Immutable
internal interface AndroidPagePayload {
    val permission: String? get() = null
}

@Immutable
internal interface SpecialAndroidPagePayload : AndroidPagePayload {
    fun isGranted(context: Context): Boolean
    fun ask(context: Context)
}
