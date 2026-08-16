plugins {
    // Kmp library
    alias(libs.plugins.echoflow.kmp.library)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            // Koin android
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            // Api
            implementation(projects.core.preferences.api)

            // Datastore
            implementation(libs.androidx.datastore.core)
            implementation(libs.androidx.datastore.preferences)
            // Koin
            implementation(libs.koin.core)
        }
        jvmMain.dependencies {

        }
    }
}