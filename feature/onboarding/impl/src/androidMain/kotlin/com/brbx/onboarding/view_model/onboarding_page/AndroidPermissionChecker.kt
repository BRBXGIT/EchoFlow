package com.brbx.onboarding.view_model.onboarding_page

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.PowerManager
import android.provider.Settings
import androidx.core.content.ContextCompat

internal class AndroidPermissionChecker(
    private val context: Context,
) : PermissionChecker {
    @SuppressLint("BatteryLife")
    override fun isPermissionGranted(permission: String): Boolean =
        if (permission == Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS) {
            val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
            powerManager.isIgnoringBatteryOptimizations(context.packageName)
        } else {
            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }
}
