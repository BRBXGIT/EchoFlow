plugins {
    // Kmp library
    alias(libs.plugins.echoflow.kmp.library)
    // Auth config
    alias(libs.plugins.echoflow.auth.config)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Api
            implementation(projects.core.data.api)
            implementation(projects.core.preferences.api)
            implementation(projects.core.network.api)

            // Libs
            implementation(libs.koin.core)
            implementation(libs.okio)
            implementation(libs.ktor.http)
        }
    }
}