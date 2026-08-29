plugins {
    // Kmp library
    alias(libs.plugins.echoflow.kmp.library)
    // Compose
    alias(libs.plugins.echoflow.compose.multiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Libs
            api(libs.navigation3.runtime)
            api(libs.koin.compose.navigation3)
            api(libs.compose.material3.adaptive.navigation3)
            implementation(libs.compose.ui)
            implementation(libs.compose.runtime)
            implementation(libs.compose.resources)
        }
    }
}