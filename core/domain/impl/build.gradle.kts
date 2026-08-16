plugins {
    // Kmp library
    alias(libs.plugins.echoflow.kmp.library)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Data
            implementation(projects.core.data.api)
            // Koin
            implementation(libs.koin.core)
        }
    }
}