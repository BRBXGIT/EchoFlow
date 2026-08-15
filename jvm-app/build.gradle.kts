plugins {
    // Jvm app
    alias(libs.plugins.echoflow.jvm.app)
    // Compose multiplatform
    alias(libs.plugins.echoflow.compose.multiplatform)
}

dependencies {

    implementation(compose.desktop.currentOs)
}