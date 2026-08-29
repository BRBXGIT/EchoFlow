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
            // Preferences
            implementation(projects.core.preferences.api)

            // Koin
            implementation(libs.koin.core)
            // Okio
            implementation(libs.okio)
            // Ktor http
            implementation(libs.ktor.http)
        }
    }
}