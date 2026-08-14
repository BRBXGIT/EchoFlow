package com.brbx.convention

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryExtension
import com.brbx.convention.config.androidNamespace
import com.brbx.convention.config.configureJvmToolchain
import com.brbx.convention.constants.Android
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal class KmpLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(receiver = target) {
            pluginManager.apply("com.android.kotlin.multiplatform.library")
            pluginManager.apply("org.jetbrains.kotlin.multiplatform")

            configureKotlinMultiplatformExtension()
            configureKotlinMultiplatformAndroidLibraryExtension()
        }
    }

    private fun Project.configureKotlinMultiplatformExtension() {
        extensions.configure<KotlinMultiplatformExtension> {
            configureKmpTargets()
        }
    }

    private fun Project.configureKotlinMultiplatformAndroidLibraryExtension() {
        extensions.configure<KotlinMultiplatformAndroidLibraryExtension> {
            configureAndroid(androidExtension = this)
        }
    }

    private fun KotlinMultiplatformExtension.configureKmpTargets() {
        jvm()
        configureJvmToolchain()
    }

    private fun Project.configureAndroid(
        androidExtension: KotlinMultiplatformAndroidLibraryExtension,
    ) {
        androidExtension.apply {
            namespace = androidNamespace
            compileSdk {
                version = release(version = Android.CompileSdk)
            }
            minSdk {
                version = release(version = Android.MinSdk)
            }
        }
    }
}