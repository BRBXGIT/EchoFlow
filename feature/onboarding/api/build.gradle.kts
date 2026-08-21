plugins {
    // Kmp library
    alias(libs.plugins.echoflow.kmp.library)
    // Serialization
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Navigation
            api(projects.feature.navigation.api)

            // Serialization
            implementation(libs.kotlinx.serialization.core)
        }
    }
}