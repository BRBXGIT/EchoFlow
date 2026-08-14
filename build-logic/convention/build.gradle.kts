import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.example.build_logic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradle.plugin.lib)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.compose.gradle.plugin)
    compileOnly(libs.compose.compiler.gradle.plugin)
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
    }
}