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
            implementation(projects.core.debug.api)
            // Other
            implementation(projects.core.designSystem.theme)

            // Libs
            api(libs.compose.material3)
            implementation(libs.compose.resources)
            implementation(libs.coil.compose)
            implementation(libs.coil.core)
            implementation(libs.coil.network.ktor)
        }
    }
}