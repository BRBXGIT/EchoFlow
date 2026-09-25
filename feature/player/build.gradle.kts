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
            implementation(projects.core.domain.api)
            implementation(projects.core.debug.api)
            // Other
            implementation(projects.feature.common)
            implementation(projects.core.designSystem.theme)
            implementation(projects.core.designSystem.components)

            // Libs
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.kotlinx.collections.immutable)
            implementation(libs.solar)
            implementation(libs.compose.resources)
        }
    }
}