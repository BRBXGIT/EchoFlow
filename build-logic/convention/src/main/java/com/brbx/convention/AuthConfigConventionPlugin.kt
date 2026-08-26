package com.brbx.convention

import com.brbx.convention.config.configureBuildConfig
import com.brbx.convention.config.configureLocalProperties
import com.brbx.convention.config.getOrEmpty
import com.brbx.convention.constants.Auth
import org.gradle.api.Plugin
import org.gradle.api.Project

internal class AuthConfigConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(receiver = target) {
            pluginManager.apply("com.github.gmazzo.buildconfig")

            val localProperties = configureLocalProperties()

            val clientId = localProperties.getOrEmpty(key = Auth.ClientId)
            val clientSecret = localProperties.getOrEmpty(key = Auth.ClientSecret)
            val redirectUri = localProperties.getOrEmpty(key = Auth.RedirectUri)
            val responseType = localProperties.getOrEmpty(key = Auth.ResponseType)

            configureBuildConfig(generatedClassName = Auth.ObjectName) {
                buildConfigField("String", Auth.ClientId, "\"$clientId\"")
                buildConfigField("String", Auth.ClientSecret, "\"$clientSecret\"")
                buildConfigField("String", Auth.RedirectUri, "\"$redirectUri\"")
                buildConfigField("String", Auth.ResponseType, "\"$responseType\"")
            }
        }
    }
}