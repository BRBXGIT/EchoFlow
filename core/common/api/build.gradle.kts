plugins {
    // Kotlin library
    alias(libs.plugins.echoflow.kotlin.library)
}

dependencies {
    // Coroutines
    api(libs.kotlinx.coroutines.core)
    // Koin
    api(libs.koin.core)
}
