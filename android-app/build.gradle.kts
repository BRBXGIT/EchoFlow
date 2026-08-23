plugins {
    // Android app
    alias(libs.plugins.echoflow.android.app)
    // Compose
    alias(libs.plugins.compose.compiler)
}

dependencies {
    // Common app
    implementation(projects.commonApp)

    // Activity compose
    implementation(libs.androidx.activity.compose)
    // Koin
    implementation(libs.koin.android)
}