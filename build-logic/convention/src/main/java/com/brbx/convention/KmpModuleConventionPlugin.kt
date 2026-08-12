package com.brbx.convention

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryExtension
import com.brbx.convention.config.configureAndroidTarget
import com.brbx.convention.config.configureKmpTargets
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpModuleConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(receiver = target) {
            extensions.configure<KotlinMultiplatformExtension> {
                configureKmpTargets()
            }
            extensions.configure<KotlinMultiplatformAndroidLibraryExtension> {
                configureAndroidTarget(androidExtension = this)
            }
        }
    }
}