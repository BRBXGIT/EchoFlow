plugins {
    // KMP library
    alias(libs.plugins.echoflow.kmp.library)
    // Compose
    alias(libs.plugins.echoflow.compose.multiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Navigation
            implementation(projects.feature.navigation.api)

            // Brbx mvi
            api(libs.brbx.mvi)
            // Koin
            implementation(libs.koin.core)
            // Compose resources
            implementation(libs.compose.resources)
            // Design components
            implementation(projects.core.designSystem.components)
        }
    }
}