plugins {
    // Kmp library
    alias(libs.plugins.echoflow.kmp.library)
    // Compose
    alias(libs.plugins.echoflow.compose.multiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Feature common
            implementation(projects.feature.common)
            // Theme
            implementation(projects.core.designSystem.theme)
            // Components
            implementation(projects.core.designSystem.components)
            // Debug
            implementation(projects.core.debug)

            // Compose resources
            implementation(libs.compose.resources)
            // Solar
            implementation(libs.solar)
            // Koin
            implementation(libs.koin.core.viewmodel)
            implementation(libs.koin.compose.viewmodel)
        }
    }
}