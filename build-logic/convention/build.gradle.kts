import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.example.build_logic"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_21
    }
}

dependencies {
    // Android
    compileOnly(libs.android.gradle.plugin.lib)
    // Kotlin
    compileOnly(libs.kotlin.gradle.plugin)
    // Compose
    compileOnly(libs.compose.gradle.plugin)
    compileOnly(libs.compose.compiler.gradle.plugin)
    // Build-сonfig
    compileOnly(libs.buildconfig.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApp") {
            id = libs.plugins.echoflow.android.app.get().pluginId
            implementationClass = "com.brbx.convention.AndroidAppConventionPlugin"
        }

        register("jvmApp") {
            id = libs.plugins.echoflow.jvm.app.get().pluginId
            implementationClass = "com.brbx.convention.JvmAppConventionPlugin"
        }

        register("kmpLibrary") {
            id = libs.plugins.echoflow.kmp.library.get().pluginId
            implementationClass = "com.brbx.convention.KmpLibraryConventionPlugin"
        }

        register("kotlinLibrary") {
            id = libs.plugins.echoflow.kotlin.library.get().pluginId
            implementationClass = "com.brbx.convention.KotlinLibraryConventionPlugin"
        }

        register("composeMultiplatform") {
            id = libs.plugins.echoflow.compose.multiplatform.get().pluginId
            implementationClass = "com.brbx.convention.ComposeMultiplatformConventionPlugin"
        }

        register("authConfig") {
            id = libs.plugins.echoflow.auth.config.get().pluginId
            implementationClass = "com.brbx.convention.AuthConfigConventionPlugin"
        }

        register("topLeveCKonfig") {
            id = libs.plugins.echoflow.top.level.config.get().pluginId
            implementationClass = "com.brbx.convention.TopLevelConfigConventionPlugin"
        }
    }
}