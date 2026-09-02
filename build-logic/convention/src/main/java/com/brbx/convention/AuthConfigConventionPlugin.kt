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
            val jvmPort =  localProperties.getOrEmpty(key = Auth.JvmPort).toIntOrNull()

            configureBuildConfig(generatedClassName = Auth.ObjectName) {
                buildConfigField(Auth.ClientId, "\"$clientId\"")
                buildConfigField(Auth.ClientSecret, "\"$clientSecret\"")
                buildConfigField(Auth.ResponseType, "\"$responseType\"")
                buildConfigField(Auth.AuthBasePath, "\"$authBasePath\"")
                buildConfigField(Auth.AuthGrantType, "\"$authGrantType\"")
                buildConfigField(Auth.RefreshGrantType, "\"$refreshGrantType\"")

                buildConfigField(Auth.AndroidRedirectUri, "\"$androidRedirectUri\"")
                buildConfigField(Auth.JvmScheme, "\"$jvmScheme\"")
                buildConfigField(Auth.JvmHost, "\"$jvmHost\"")
                buildConfigField(Auth.JvmPort, "$jvmPort")
            }
        }
    }
}