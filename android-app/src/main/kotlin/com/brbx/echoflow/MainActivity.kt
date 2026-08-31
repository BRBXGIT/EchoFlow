package com.brbx.echoflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.brbx.common_app.EchoFlowApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val deeplink = if (intent.data == null) {
            null
        } else {
            intent.data.toString()
        }

        enableEdgeToEdge()
        setContent {
            EchoFlowApp(deeplink)
        }
    }
}