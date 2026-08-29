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
            implementation(projects.core.domain.api)
            implementation(projects.feature.navigation.api)
            // Impl
            implementation(projects.core.common.impl)
            implementation(projects.core.data.impl)
            implementation(projects.core.domain.impl)
            implementation(projects.core.preferences.impl)
            implementation(projects.feature.navigation.impl)
            implementation(projects.feature.onboarding.impl)
            implementation(projects.feature.home.impl)
            // Ohter
            implementation(projects.core.designSystem.theme)
            implementation(projects.feature.common)

            // Libs
            implementation(libs.koin.compose.viewmodel)
        }
    }
}