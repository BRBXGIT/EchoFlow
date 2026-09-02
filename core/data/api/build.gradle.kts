plugins {
    // Kmp library
    alias(libs.plugins.echoflow.kmp.library)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Api
            api(projects.core.domain.api)
        }
    }
}