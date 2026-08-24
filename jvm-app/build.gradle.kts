plugins {
    // Jvm app
    alias(libs.plugins.echoflow.jvm.app)
    // Compose multiplatform
    alias(libs.plugins.echoflow.compose.multiplatform)
}

dependencies {
    // Common app
    implementation(projects.commonApp)

    // Compose desktop
    implementation(compose.desktop.currentOs)
    // Coroutines swing
    implementation(libs.kotlinx.coroutines.swing)
}