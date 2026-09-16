plugins {
    // Kmp library
    alias(libs.plugins.echoflow.kmp.library)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Api
            implementation(projects.core.data.api)

            // Libs
            implementation(libs.koin.core)
        }

        commonTest.dependencies {
            // Libs
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}