package com.brbx.convention

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryExtension
import org.gradle.api.Project

internal fun Project.setupAndroidTarget(
    androidExtension: KotlinMultiplatformAndroidLibraryExtension,
) {
    val modulePath = project.path
        .split(":")
        .filter { it.isNotEmpty() }
        .joinToString(separator = ".") { it.replace("-", "_") }

    androidExtension.apply {
        namespace = if (modulePath.isNotEmpty()) {
            "com.brbx.$modulePath"
        } else "com.brbx"

        compileSdk {
            version = release(version = Android.CompileSdk)
        }
        minSdk {
            version = release(version = Android.MinSdk)
        }
    }
}