plugins {
    // Kmp library
    alias(libs.plugins.echoflow.kmp.library)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Libs
            api(libs.kotlinx.coroutines.core)
            api(libs.androidx.paging.core)
        }
    }
}