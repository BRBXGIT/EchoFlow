plugins {
    // Jvm app
    alias(libs.plugins.echoflow.jvm.app)
    // Compose multiplatform
    alias(libs.plugins.echoflow.compose.multiplatform)
    // Top level config
    alias(libs.plugins.echoflow.top.level.config)
}

dependencies {
    // Api
    implementation(projects.core.common.api)
    // Other
    implementation(projects.commonApp)
    implementation(projects.core.designSystem.theme)
    implementation(projects.core.designSystem.components)

    // Libs
    implementation(libs.kotlinx.coroutines.swing)
    implementation(libs.solar)
    implementation(compose.desktop.currentOs)
}