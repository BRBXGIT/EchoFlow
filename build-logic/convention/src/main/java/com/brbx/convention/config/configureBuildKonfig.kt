package com.brbx.convention.config

import com.codingfeline.buildkonfig.gradle.BuildKonfigExtension
import org.gradle.api.Project
import java.util.Properties
import org.gradle.api.Action

internal fun Project.configureBuildKonfig(
    generatedObjectName: String = "BuildKonfig",
    block: Action<in BuildKonfigExtension>,
) {
    extensions.configure(BuildKonfigExtension::class.java) {
        packageName.set(project.moduleNamespace)
        objectName.set(generatedObjectName)
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