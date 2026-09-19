plugins {
    // Android app
    alias(libs.plugins.echoflow.android.app)
    // Compose
    alias(libs.plugins.compose.compiler)
}

dependencies {
    // Impl
    implementation(projects.core.data.impl)
    // Other
    implementation(projects.commonApp)

    // Libs
    implementation(libs.androidx.activity.compose)
    implementation(libs.koin.android)
}