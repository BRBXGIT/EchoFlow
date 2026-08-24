package com.brbx.onboarding.view_model.onboarding_page

internal class JvmPermissionChecker : PermissionChecker {
    override fun isPermissionGranted(permission: String): Boolean = false
}
