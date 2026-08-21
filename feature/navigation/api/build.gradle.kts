plugins {
    // Kmp library
    alias(libs.plugins.echoflow.kmp.library)
    // Compose
    alias(libs.plugins.echoflow.compose.multiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Koin navigation
            api(libs.koin.compose.navigation3)
            // Navigation 3
            implementation(libs.navigation3.runtime)
            // Compose graphics
            implementation(libs.compose.ui)
            // Compose runtime
            implementation(libs.compose.runtime)
            // Compose resources
            implementation(libs.compose.resources)
        }
    }
}