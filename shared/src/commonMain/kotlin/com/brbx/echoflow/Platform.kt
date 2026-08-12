package com.brbx.echoflow

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform