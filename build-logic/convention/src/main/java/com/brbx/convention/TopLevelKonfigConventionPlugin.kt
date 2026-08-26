package com.brbx.convention

import com.brbx.convention.config.configureBuildKonfig
import com.brbx.convention.config.configureLocalProperties
import com.brbx.convention.config.getOrEmpty
import com.brbx.convention.constants.Auth
import com.brbx.convention.constants.TopLevelConfig
import com.codingfeline.buildkonfig.compiler.FieldSpec
import com.codingfeline.buildkonfig.gradle.BuildKonfigExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

internal class TopLevelKonfigConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(receiver = target) {
            pluginManager.apply("com.codingfeline.buildkonfig")

            val localProperties = configureLocalProperties()

            val appName = localProperties.getOrEmpty(key = TopLevelConfig.AppName)

            configureBuildKonfig(generatedObjectName = TopLevelConfig.ObjectName) {
                defaultConfigs {
                    buildConfigField(type = FieldSpec.Type.STRING, name = TopLevelConfig.AppName, value = "\"$appName\"")
                }
            }
        }
    }
}