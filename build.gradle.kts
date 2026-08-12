plugins {
    // Application (used in convention)
    alias(libs.plugins.android.application) apply false
    // Android
    alias(libs.plugins.android.library) apply false
    // Android mp (used in convention)
    alias(libs.plugins.android.multiplatform.library) apply false
    // Compose multplatform (used in convention)
    alias(libs.plugins.compose.multiplatform) apply false
    // Compose compiler (used in convention)
    alias(libs.plugins.compose.compiler) apply false
    // Kotlin jvm (used in convention)
    alias(libs.plugins.kotlin.jvm) apply false
    // Serialization
    alias(libs.plugins.kotlin.serialization) apply false
    // Kmp (used in convention)
    alias(libs.plugins.kotlin.multiplatform) apply false
}