plugins {
    // Kotlin library
    alias(libs.plugins.echoflow.kotlin.library)
}

dependencies {
    // Api
    api(projects.core.domain.api)
}