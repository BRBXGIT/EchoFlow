package com.brbx.convention

import com.brbx.convention.constants.Java
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal class KotlinLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(receiver = target) {
            pluginManager.apply("org.jetbrains.kotlin.jvm")

            configureJavaPluginExtension()
            configureKotlinCompile()
        }
    }

    private fun Project.configureKotlinCompile() {
        tasks.withType(KotlinCompile::class.java).configureEach {
            compilerOptions {
                jvmTarget.set(Java.JvmV)
            }
        }
    }

    private fun Project.configureJavaPluginExtension() {
        extensions.configure<JavaPluginExtension> {
            configureCompatibility()
        }
    }

    private fun JavaPluginExtension.configureCompatibility() {
        sourceCompatibility = Java.JavaV
        targetCompatibility = Java.JavaV
    }
}