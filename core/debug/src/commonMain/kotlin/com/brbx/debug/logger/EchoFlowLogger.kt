package com.brbx.debug.logger

expect object EchoFlowLogger {
    fun d(tag: String, message: String, throwable: Throwable? = null)
    fun i(tag: String, message: String, throwable: Throwable? = null)
    fun w(tag: String, message: String, throwable: Throwable? = null)
    fun e(tag: String, message: String, throwable: Throwable? = null)
}

fun logD(tag: String, message: String, throwable: Throwable? = null) {
    EchoFlowLogger.d(tag, message, throwable)
}

fun logI(tag: String, message: String, throwable: Throwable? = null) {
    EchoFlowLogger.i(tag, message, throwable)
}

fun logW(tag: String, message: String, throwable: Throwable? = null) {
    EchoFlowLogger.w(tag, message, throwable)
}

fun logE(tag: String, message: String, throwable: Throwable? = null) {
    EchoFlowLogger.e(tag, message, throwable)
}