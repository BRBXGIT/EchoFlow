package com.brbx.echoflow

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.brbx.common_app.EchoFlowApp
import com.brbx.common_app.setupKoin
import com.brbx.jvm_app.TopLevelConfig

fun main() {
    application {
        setupKoin()

        val windowState = rememberWindowState(
            size = DpSize(width = 1024.dp, height = 768.dp),
            position = WindowPosition(alignment = Alignment.Center)
        )

        Window(
            onCloseRequest = ::exitApplication,
            title = TopLevelConfig.appName,
            state = windowState,
        ) {
            EchoFlowApp(deeplink = null)
        }
    }
}