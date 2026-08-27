plugins {
    // Kmp library
    alias(libs.plugins.echoflow.kmp.library)
    // Compose
    alias(libs.plugins.echoflow.compose.multiplatform)
    // Auth config
    alias(libs.plugins.echoflow.auth.config)
    // Koin
    alias(libs.plugins.koin.compiler)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Api
            api(projects.feature.onboarding.api)
            // Feature common
            implementation(projects.feature.common)
            // Navigation
            implementation(projects.feature.navigation.api)
            // Theme
            implementation(projects.core.designSystem.theme)
            // Components
            implementation(projects.core.designSystem.components)
            // Debug
            implementation(projects.core.debug.api)

            // Compose resources
            implementation(libs.compose.resources)
            // Solar
            implementation(libs.solar)
            // Koin
            implementation(libs.koin.compose.viewmodel)
        }
    }
}