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
            // Debug
            implementation(projects.core.debug)

            // Compose resources
            implementation(libs.compose.resources)
            // Solar
            implementation(libs.solar)
            // Koin ViewModel
            implementation(libs.koin.core.viewmodel)
        }
        androidMain.dependencies {
            // Compose preview
            implementation(libs.compose.ui.tooling)
        }
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.ui.tooling)
}