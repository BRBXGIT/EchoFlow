package com.brbx.echoflow

import android.app.Application
import com.brbx.common_app.projectModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class EchoFlowApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@EchoFlowApp)
            modules(projectModules)
        }
    }
}