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
        maven { url = uri("https://jitpack.io") }
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

// Core common
include(":core:common")
include(":core:common:api")
include(":core:common:impl")

// Data
include(":core:data")
include(":core:data:api")
include(":core:data:impl")

// Debug
include(":core:debug")
include(":core:debug:impl")
include(":core:debug:api")

// Design-system
include(":core:design-system")
include(":core:design-system:theme")
include(":core:design-system:components")

// Domain
include(":core:domain")
include(":core:domain:api")
include(":core:domain:impl")

// Preferences
include(":core:preferences")
include(":core:preferences:api")
include(":core:preferences:impl")

// Network
include(":core:network")

// Feature
include(":feature")

// Common
include(":feature:common")

// Onboarding
include(":feature:onboarding")
include(":feature:onboarding:api")
include(":feature:onboarding:impl")
