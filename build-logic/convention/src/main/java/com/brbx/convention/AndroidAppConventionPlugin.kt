package com.brbx.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryExtension
import com.brbx.convention.constants.Android
import com.brbx.convention.constants.Java
import com.brbx.convention.config.configureAndroidTarget
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidAppConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(receiver = target) {
            pluginManager.apply("com.android.application")

            extensions.configure<KotlinMultiplatformAndroidLibraryExtension> {
                configureAndroidTarget(this)
            }

            extensions.configure<ApplicationExtension> {
                compileOptions.apply {
                    sourceCompatibility = Java.JavaV
                    targetCompatibility = Java.JavaV
                }

                defaultConfig {
                    applicationId = Android.ApplicationId
                    targetSdk = Android.TargetSdk
                    versionCode = Android.VersionCode
                    versionName = Android.VersionName
                }

                buildTypes {
                    release {
                        optimization {
                            enable = false
                        }
                    }
                }
            }
        }
    }
}