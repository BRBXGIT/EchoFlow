rootProject.name = "EchoFlow"

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

// Apps
include(":android-app")
include(":jvm-app")

// Core
include(":core")

// Feature
include(":feature")

// Preferences
include(":core:preferences")
include(":core:preferences:api")
include(":core:preferences:impl")

// Network
include(":core:network")

// Domain
include(":core:domain")

// Commmon
include(":core:common")
include(":core:domain:api")
include(":core:data")
include(":core:data:api")
include(":core:data:impl")
include(":core:domain:impl")
include(":core:common:api")
include(":core:common:impl")
