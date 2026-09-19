plugins {
    // Kmp library
    alias(libs.plugins.echoflow.kmp.library)
    // Auth config
    alias(libs.plugins.echoflow.auth.config)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            // Libs
            implementation(libs.androidx.media3.exoplayer)
            implementation(libs.androidx.media3.session)
            implementation(libs.koin.android)
        }
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
        jvmMain.dependencies {
            // Api
            implementation(projects.core.localServer.api)
        }

        commonTest.dependencies {
            // Libs
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}