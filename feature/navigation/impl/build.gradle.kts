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
            implementation(projects.feature.navigation.api)
            // Other
            implementation(projects.core.designSystem.theme)

            // Libs
            implementation(libs.navigation3.ui)
        }
    }
}