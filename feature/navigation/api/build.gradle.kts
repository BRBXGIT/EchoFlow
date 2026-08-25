plugins {
    // Kmp library
    alias(libs.plugins.echoflow.kmp.library)
    // Compose
    alias(libs.plugins.echoflow.compose.multiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Navigation 3
            api(libs.navigation3.runtime)
            // Koin navigation
            api(libs.koin.compose.navigation3)
            // Adaptive
            api(libs.compose.material3.adaptive.navigation3)
            // Compose graphics
            implementation(libs.compose.ui)
            // Compose runtime
            implementation(libs.compose.runtime)
            // Compose resources
            implementation(libs.compose.resources)
        }
    }
}