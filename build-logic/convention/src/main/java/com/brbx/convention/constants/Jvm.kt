package com.brbx.convention.constants

import org.jetbrains.compose.desktop.application.dsl.TargetFormat

internal object Jvm {
    const val MainClass = "MainKt"
    const val PackageName = "EchoFlow"
    const val PackageVersion = "1.0.0"
    const val UpgradeUuid = "b1407b29-f554-431c-a204-6479ae1a08bc"
    const val DesktopExtensionName = "desktop"

    val TargetFormats = arrayOf(TargetFormat.Msi)
}