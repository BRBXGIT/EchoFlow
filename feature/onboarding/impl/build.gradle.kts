plugins {
    // Kmp library
    alias(libs.plugins.echoflow.kmp.library)
    // Compose
    alias(libs.plugins.echoflow.compose.multiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Brbx mvi
            implementation(libs.brbx.mvi)
            // Material 3
            implementation(libs.compose.material3)
            // Compose resources
            implementation(libs.compose.resources)
            // Solar
            implementation(libs.solar)
        }
    }
}