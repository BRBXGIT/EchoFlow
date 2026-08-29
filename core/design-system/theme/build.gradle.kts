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
            api(libs.compose.material3)

            // Libs
            implementation(libs.materialKolor)
            implementation(libs.compose.resources)
        }
    }
}