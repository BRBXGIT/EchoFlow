plugins {
    // Kmp library
    alias(libs.plugins.echoflow.kmp.library)
    // Compose
    alias(libs.plugins.echoflow.compose.multiplatform) // TODO Remove later
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Api
            api(projects.feature.home.api)

            // Libs
            implementation(projects.core.designSystem.theme)
        }
    }
}