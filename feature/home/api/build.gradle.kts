plugins {
    // Kmp library
    alias(libs.plugins.echoflow.kmp.library)
    // Compose
    alias(libs.plugins.echoflow.compose.multiplatform)
    // Serialization
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Api
            api(projects.feature.navigation.api)

            // Libs
            implementation(libs.kotlinx.serialization.core)
            implementation(libs.solar)
            implementation(libs.compose.resources)
        }
    }
}