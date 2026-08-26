package com.brbx.convention.config

import com.github.gmazzo.buildconfig.BuildConfigExtension
import org.gradle.api.Project
import java.util.Properties
import org.gradle.api.Action

internal fun Project.configureBuildConfig(
    generatedClassName: String = "BuildConfig",
    block: Action<in BuildConfigExtension>,
) {
    extensions.configure(BuildConfigExtension::class.java) {
        packageName.set(project.moduleNamespace)
        className.set(generatedClassName)
        block.execute(this)
    }
}

internal fun Project.configureLocalProperties(): Properties {
    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localProperties.load(localPropertiesFile.inputStream())
    }
    return localProperties
}

internal fun Properties.getOrEmpty(key: String): String =
    this.getProperty(key)
        ?: System.getenv(key) ?: ""