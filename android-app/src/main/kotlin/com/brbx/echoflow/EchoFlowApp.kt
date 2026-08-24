package com.brbx.echoflow

import android.app.Application
import com.brbx.common_app.setupKoin
import org.koin.android.ext.koin.androidContext

class EchoFlowApp : Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin {
            androidContext(this@EchoFlowApp)
        }
    }
}