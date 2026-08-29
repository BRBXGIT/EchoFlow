plugins {
    // Kmp library
    alias(libs.plugins.echoflow.kmp.library)
    // Compose
    alias(libs.plugins.echoflow.compose.multiplatform)
    // Koin compiler
    alias(libs.plugins.koin.compiler)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Api
            api(projects.feature.onboarding.api)
            // Feature common
            implementation(projects.feature.common)
            // Theme
            implementation(projects.core.designSystem.theme)
            // Components
            implementation(projects.core.designSystem.components)
            // Debug
            implementation(projects.core.debug.api)
            // Domain
            implementation(projects.core.domain.api)

            // Compose resources
            implementation(libs.compose.resources)
            // Solar
            implementation(libs.solar)
            // Koin
            implementation(libs.koin.compose.viewmodel)
        }
    }
}