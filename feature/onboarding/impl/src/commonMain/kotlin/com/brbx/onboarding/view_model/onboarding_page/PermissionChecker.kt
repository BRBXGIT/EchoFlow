package com.brbx.onboarding.view_model.onboarding_page

internal interface PermissionChecker {
    fun isPermissionGranted(permission: String): Boolean
}
