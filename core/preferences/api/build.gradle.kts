plugins {
    // Kotlin library
    alias(libs.plugins.echoflow.kotlin.library)
}

dependencies {
    // Libs
    api(libs.kotlinx.coroutines.core)
    implementation(libs.koin.core)
}