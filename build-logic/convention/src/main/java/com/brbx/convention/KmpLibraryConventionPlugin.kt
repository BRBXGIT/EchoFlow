package com.brbx.convention

import com.android.build.api.dsl.LibraryExtension
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
            with(receiver = pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.multiplatform")
            }

            configureKotlinMultiplatformExtension()
        }
    }

    private fun Project.configureKotlinMultiplatformExtension() {
        extensions.configure<KotlinMultiplatformExtension> {
            configureKmpTargets()
            configureAndroid(this)
        }
    }

    private fun KotlinMultiplatformExtension.configureKmpTargets() {
        jvm()
        configureJvmToolchain()
    }

    private fun Project.configureAndroid(kmpExtension: KotlinMultiplatformExtension) {
        kmpExtension.androidTarget()
        extensions.configure<LibraryExtension> {
            namespace = androidNamespace
            compileSdk = Android.CompileSdk
            defaultConfig {
                minSdk = Android.MinSdk
            }
        }
    }
}