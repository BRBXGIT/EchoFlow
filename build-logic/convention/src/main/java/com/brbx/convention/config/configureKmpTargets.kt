package com.brbx.convention.config

import com.brbx.convention.constants.Java
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun KotlinMultiplatformExtension.configureKmpTargets() {
    jvm()
    jvmToolchain(jdkVersion = Java.JdkV)
}