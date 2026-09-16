plugins {
    // Kmp library
    alias(libs.plugins.echoflow.kmp.library)
    // Compose
    alias(libs.plugins.echoflow.compose.multiplatform)
    // Koin compiler
    alias(libs.plugins.koin.compiler)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Api
            api(projects.feature.onboarding.api)
            api(projects.feature.home.api)
            implementation(projects.core.debug.api)
            implementation(projects.core.domain.api)
            implementation(projects.core.common.api)
            // Other
            implementation(projects.feature.common)
            implementation(projects.core.designSystem.theme)
            implementation(projects.core.designSystem.components)

            // Libs
            implementation(libs.compose.resources)
            implementation(libs.solar)
            implementation(libs.koin.compose.viewmodel)
        }

        commonTest.dependencies {
            // Libs
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}