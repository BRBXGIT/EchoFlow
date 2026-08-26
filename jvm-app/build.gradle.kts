plugins {
    // Jvm app
    alias(libs.plugins.echoflow.jvm.app)
    // Compose multiplatform
    alias(libs.plugins.echoflow.compose.multiplatform)
    // Top level config
    alias(libs.plugins.echoflow.top.level.config)
}

dependencies {
    // Core common
    implementation(projects.core.common.api)
    // Common app
    implementation(projects.commonApp)

    // Compose desktop
    implementation(compose.desktop.currentOs)
    // Coroutines swing
    implementation(libs.kotlinx.coroutines.swing)
}