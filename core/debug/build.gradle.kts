plugins {
    // Kmp library
    alias(libs.plugins.echoflow.kmp.library)
    // Compose
    alias(libs.plugins.echoflow.compose.multiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Theme
            implementation(projects.core.designSystem.theme)

            // Compose preview
            api(libs.compose.preview)
        }
        androidMain.dependencies {
            // Compose preview
            implementation(libs.compose.ui.tooling)
        }
    }

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}