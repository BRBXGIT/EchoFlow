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
        register("kmpShared") {
            id = "echoflow.kmp.shared"
            implementationClass = "com.brbx.convention.KmpSharedConventionPlugin"
        }
        register("androidApp") {
            id = "echoflow.android.app"
            implementationClass = "com.brbx.convention.AndroidAppConventionPlugin"
        }
        register("jvmApp") {
            id = "echoflow.jvm.app"
            implementationClass = "com.brbx.convention.JvmAppConventionPlugin"
        }
        register("compose") {
            id = "echoflow.compose"
            implementationClass = "com.brbx.convention.ComposeConventionPlugin"
        }
        register("kotlinLibrary") {
            id = "echoflow.kotlin.library"
            implementationClass = "com.brbx.convention.KotlinLibraryConventionPlugin"
        }
    }
}