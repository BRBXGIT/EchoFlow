plugins {
    // Kmp library
    alias(libs.plugins.echoflow.kmp.library)
    // Compose
    alias(libs.plugins.echoflow.compose.multiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Material 3
            api(libs.compose.material3)
            // Compose resources
            implementation(libs.compose.resources)
        }
    }
}