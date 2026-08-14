package com.brbx.convention.config

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryExtension
import com.brbx.convention.constants.Android
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureKotlinMultiplatformAndroidLibraryExtension() {
    extensions.configure<KotlinMultiplatformAndroidLibraryExtension> {
        configureAndroidTarget(androidExtension = this)
    }
}

private fun Project.configureAndroidTarget(
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