plugins {
    // KMP library
    alias(libs.plugins.echoflow.kmp.library)
    // Compose
    alias(libs.plugins.echoflow.compose.multiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Api
            api(projects.feature.navigation.api)
            // Impl
            implementation(projects.feature.navigation.impl)
            implementation(projects.core.designSystem.components)

            // Libs
            api(libs.brbx.mvi)
            implementation(libs.koin.core)
            implementation(libs.compose.resources)
        }
    }
}