package com.brbx.convention

import com.brbx.convention.config.configureBuildConfig
import com.brbx.convention.config.configureLocalProperties
import com.brbx.convention.config.getOrEmpty
import com.brbx.convention.constants.Auth
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.buildConfigField

internal class AuthConfigConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(receiver = target) {
            pluginManager.apply("com.github.gmazzo.buildconfig")

            val localProperties = configureLocalProperties()

            val clientId = localProperties.getOrEmpty(key = Auth.ClientId)
            val clientSecret = localProperties.getOrEmpty(key = Auth.ClientSecret)
            val responseType = localProperties.getOrEmpty(key = Auth.ResponseType)
            val authBasePath = localProperties.getOrEmpty(key = Auth.AuthBasePath)
            val authGrantType = localProperties.getOrEmpty(key = Auth.AuthGrantType)
            val refreshGrantType = localProperties.getOrEmpty(key = Auth.RefreshGrantType)

            val androidRedirectUri = localProperties.getOrEmpty(key = Auth.AndroidRedirectUri)
            val jvmScheme = localProperties.getOrEmpty(key = Auth.JvmScheme)
            val jvmHost =  localProperties.getOrEmpty(key = Auth.JvmHost)
            val jvmPath =  localProperties.getOrEmpty(key = Auth.JvmPath)
            val jvmPort =  localProperties.getOrEmpty(key = Auth.JvmPort).toIntOrNull()

            configureBuildConfig(generatedClassName = Auth.ObjectName) {
                buildConfigField(name = Auth.ClientId, value = clientId)
                buildConfigField(name = Auth.ClientSecret, value = clientSecret)
                buildConfigField(name = Auth.ResponseType, value = responseType)
                buildConfigField(name = Auth.AuthBasePath, value = authBasePath)
                buildConfigField(name = Auth.AuthGrantType, value = authGrantType)
                buildConfigField(name = Auth.RefreshGrantType, value = refreshGrantType)

                buildConfigField(name = Auth.AndroidRedirectUri, value = androidRedirectUri)
                buildConfigField(name = Auth.JvmScheme, value = jvmScheme)
                buildConfigField(name = Auth.JvmHost, value = jvmHost)
                buildConfigField(name = Auth.JvmPort, value = jvmPort)
                buildConfigField(name = Auth.JvmPath, value = jvmPath)
            }
        }
    }
}