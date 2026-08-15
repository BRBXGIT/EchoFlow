package com.brbx.echoflow

import androidx.compose.material.Text
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "EchoFlow",
    ) {
        Text(
            text = "Hello"
        )
    }
}