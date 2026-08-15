package com.brbx.convention

import com.brbx.convention.config.configureJvmToolchain
import com.brbx.convention.constants.Jvm
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.compose.desktop.DesktopExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

internal class JvmAppConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(receiver = target) {
            with(receiver = pluginManager) {
                apply("org.jetbrains.kotlin.jvm")
                apply("org.jetbrains.compose")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            configureKotlin()
            configureComposeDesktop()
        }
    }

    private fun Project.configureKotlin() {
        extensions.configure<KotlinJvmProjectExtension> {
            configureJvmToolchain()
        }
    }

    private fun Project.configureComposeDesktop() {
        val compose = extensions.getByType<ComposeExtension>()

        compose.extensions.configure<DesktopExtension>(Jvm.DesktopExtensionName) {
            application {
                mainClass = Jvm.MainClass

                nativeDistributions {
                    targetFormats(*Jvm.TargetFormats)
                    packageName = Jvm.PackageName
                    packageVersion = Jvm.PackageVersion

                    windows {
                        upgradeUuid = Jvm.UpgradeUuid
                    }
                }
            }
        }
    }
}