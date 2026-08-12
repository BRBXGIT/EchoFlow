package com.brbx.convention

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.setupKmpTargets(
    kmpExtension: KotlinMultiplatformExtension
) {
    kmpExtension.apply {
        jvm()
        jvmToolchain(jdkVersion = Java.JdkV)
    }
}