package com.brbx.convention

import com.brbx.convention.config.configureJvmToolchain
import com.brbx.convention.config.configureKotlinMultiplatformAndroidLibraryExtension
import com.brbx.convention.constants.Java
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal class KmpModuleConventionPlugin : Plugin<Project> {
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

    private fun KotlinMultiplatformExtension.configureKmpTargets() {
        jvm()
        configureJvmToolchain()
    }
}