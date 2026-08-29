plugins {
    // Kotlin library
    alias(libs.plugins.echoflow.kotlin.library)
}

dependencies {
    // Api
    implementation(projects.core.network.api)

    // Libs
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.auth)
    implementation(libs.koin.core)
}