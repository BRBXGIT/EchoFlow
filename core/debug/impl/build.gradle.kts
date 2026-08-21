plugins {
    // Kmp library
    alias(libs.plugins.echoflow.kmp.library)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}