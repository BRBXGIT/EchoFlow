package com.brbx.echoflow

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.brbx.common_app.EchoFlowApp
import com.brbx.design_system.theme.EchoFlowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = Color.TRANSPARENT, darkScrim = Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = Color.TRANSPARENT, darkScrim = Color.TRANSPARENT
            )
        )
        super.onCreate(savedInstanceState)

        val deeplink = if (intent.data == null) {
            null
        } else {
            intent.data.toString()
        }

        setContent {
            EchoFlowTheme {
                EchoFlowApp(deeplink)
            }
        }
    }
}