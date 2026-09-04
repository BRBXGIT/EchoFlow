plugins {
    // Kmp library
    alias(libs.plugins.echoflow.kmp.library)
    // Compose
    alias(libs.plugins.echoflow.compose.multiplatform)
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