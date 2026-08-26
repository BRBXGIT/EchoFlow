package com.brbx.convention

import com.brbx.convention.config.configureBuildKonfig
import com.brbx.convention.config.configureLocalProperties
import com.brbx.convention.config.getOrEmpty
import com.brbx.convention.constants.Auth
import com.codingfeline.buildkonfig.compiler.FieldSpec
import com.codingfeline.buildkonfig.gradle.BuildKonfigExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import kotlin.jvm.java

internal class AuthKonfigConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(receiver = target) {
            pluginManager.apply("com.codingfeline.buildkonfig")

            val localProperties = configureLocalProperties()

            val clientId = localProperties.getOrEmpty(key = Auth.ClientId)
            val clientSecret = localProperties.getOrEmpty(key = Auth.ClientSecret)
            val redirectUri = localProperties.getOrEmpty(key = Auth.RedirectUri)
            val responseType = localProperties.getOrEmpty(key = Auth.ResponseType)

            configureBuildKonfig(generatedObjectName = Auth.ObjectName) {
                defaultConfigs {
                    buildConfigField(type = FieldSpec.Type.STRING, name = Auth.ClientId, value = "\"$clientId\"")
                    buildConfigField(type = FieldSpec.Type.STRING, name = Auth.ClientSecret, value = "\"$clientSecret\"")
                    buildConfigField(type = FieldSpec.Type.STRING, name = Auth.RedirectUri, value = "\"$redirectUri\"")
                    buildConfigField(type = FieldSpec.Type.STRING, name = Auth.ResponseType, value = "\"$responseType\"")
                }
            }
        }
    }
}