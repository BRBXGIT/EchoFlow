plugins {
    // Kmp library
    alias(libs.plugins.echoflow.kmp.library)
    // Auth config
    alias(libs.plugins.echoflow.auth.config)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Libs
            implementation(libs.koin.core)
        }
        jvmMain.dependencies {
            // Api
            implementation(projects.core.localServer.api)

            // Libs
            implementation(libs.ktor.server.core)
            implementation(libs.ktor.server.cio)
        }
    }
}
