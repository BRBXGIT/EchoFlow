plugins {
    // Kmp library
    alias(libs.plugins.echoflow.kmp.library)
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