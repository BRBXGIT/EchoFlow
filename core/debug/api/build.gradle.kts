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
            api(libs.compose.preview)
            // Impl
            implementation(projects.core.debug.impl)
            // Other
            implementation(projects.core.designSystem.theme)
        }
        androidMain.dependencies {
            // Libs
            implementation(libs.compose.ui.tooling)
        }
    }
}