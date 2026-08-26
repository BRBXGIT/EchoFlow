package com.brbx.convention

import com.android.build.api.dsl.ApplicationExtension
import com.brbx.convention.config.moduleNamespace
import com.brbx.convention.constants.Android
import com.brbx.convention.constants.Java
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal class AndroidAppConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(receiver = target) {
            with(receiver = pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            configureApplicationExtension()
        }
    }

    private fun Project.configureApplicationExtension() {
        extensions.configure<ApplicationExtension> {
            configureAndroid(applicationExtension = this)
            configureCompatibility()
            configureDefaultConfig()
            configureBuildTypes()
        }
    }

    private fun Project.configureAndroid(applicationExtension: ApplicationExtension) {
        applicationExtension.namespace = moduleNamespace
    }

    private fun ApplicationExtension.configureCompatibility() {
        compileOptions {
            sourceCompatibility = Java.JavaV
            targetCompatibility = Java.JavaV
        }
    }

    private fun ApplicationExtension.configureDefaultConfig() {
        defaultConfig {
            applicationId = Android.ApplicationId
            targetSdk = Android.TargetSdk
            versionCode = Android.VersionCode
            versionName = Android.VersionName
            minSdk = Android.MinSdk
            compileSdk = Android.CompileSdk
        }
    }

    private fun ApplicationExtension.configureBuildTypes() {
        buildTypes {
            release {
                optimization {
                    enable = false
                }
            }
        }
    }
}