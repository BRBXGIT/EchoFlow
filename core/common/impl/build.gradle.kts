plugins {
    // Kotlin library
    alias(libs.plugins.echoflow.kotlin.library)
}

dependencies {
    // Api
    implementation(projects.core.common.api)

    // Koin
    implementation(libs.koin.core)
}
