import org.gradle.kotlin.dsl.invoke

plugins {
    // Kmp library
    alias(libs.plugins.echoflow.kmp.library)
    // Compose
    alias(libs.plugins.echoflow.compose.multiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Api
            implementation(projects.feature.navigation.api)
            // Navigation 3
            implementation(libs.navigation3.ui)
        }
    }
}