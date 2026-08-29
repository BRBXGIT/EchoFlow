plugins {
    // Kmp library
    alias(libs.plugins.echoflow.kmp.library)
    // Top level config
    alias(libs.plugins.echoflow.top.level.config)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            // Libs
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            // Api
            implementation(projects.core.preferences.api)

            // Libs
            implementation(libs.androidx.datastore.core)
            implementation(libs.androidx.datastore.preferences)
            implementation(libs.koin.core)
        }
        jvmMain.dependencies {
            // Api
            implementation(projects.core.common.api)
        }
    }
}