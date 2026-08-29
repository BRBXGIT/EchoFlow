plugins {
    // Kotlin library
    alias(libs.plugins.echoflow.kotlin.library)
    // Auth config
    alias(libs.plugins.echoflow.auth.config)
    // Serialization
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    // Libs
    api(libs.ktor.client.core)
    implementation(libs.kotlinx.serialization.core)
}