plugins {
    // Android app
    alias(libs.plugins.echoflow.android.app)
}

dependencies {
    // Api
    implementation(projects.feature.navigation.api)
    implementation(projects.feature.onboarding.api)
    // Impl
    implementation(projects.feature.navigation.impl)
    implementation(projects.feature.onboarding.impl)

    // Activity compose
    implementation(libs.androidx.activity.compose)
}