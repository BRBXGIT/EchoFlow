plugins {
    // Kotlin library
    alias(libs.plugins.echoflow.kotlin.library)
}

dependencies {
    // Api
    implementation(projects.core.common.api)

    // Libs
    implementation(libs.koin.core)
}
