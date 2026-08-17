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
            implementation(projects.feature.common.api)
            // Theme
            implementation(projects.core.designSystem.theme)

            // Compose resources
            implementation(libs.compose.resources)
            // Solar
            implementation(libs.solar)
            // Koin ViewModel
            implementation(libs.koin.core.viewmodel)
            // Compose preview
            implementation(libs.compose.ui.tooling.preview)
        }
        androidMain.dependencies {
            // Compose preview
            implementation(libs.compose.ui.tooling)
        }
    }
}