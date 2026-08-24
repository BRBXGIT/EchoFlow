package com.brbx.echoflow

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.brbx.common_app.EchoFlowApp
import com.brbx.common_app.setupKoin

fun main() = application {
    setupKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "EchoFlow",
    ) {
        EchoFlowApp()
    }
}