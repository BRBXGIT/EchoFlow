package com.brbx.convention

import com.brbx.convention.config.configureBuildConfig
import com.brbx.convention.config.configureLocalProperties
import com.brbx.convention.config.getOrEmpty
import com.brbx.convention.constants.TopLevelConfig
import org.gradle.api.Plugin
import org.gradle.api.Project

internal class TopLevelConfigConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(receiver = target) {
            pluginManager.apply("com.github.gmazzo.buildconfig")

            val localProperties = configureLocalProperties()

            val appName = localProperties.getOrEmpty(key = TopLevelConfig.AppName)

            configureBuildConfig(generatedClassName = TopLevelConfig.ObjectName) {
                buildConfigField("String", TopLevelConfig.AppName, "\"$appName\"")
            }
        }
    }
}