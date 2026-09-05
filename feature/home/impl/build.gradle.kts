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
            api(projects.feature.home.api)
            implementation(projects.core.domain.api)
            implementation(projects.core.common.api)
            // Other
            implementation(projects.feature.common)
            implementation(projects.core.designSystem.theme)

            // Libs
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.kotlinx.collections.immutable)
        }
    }
}