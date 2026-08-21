package com.brbx.debug.logger

private enum class LogLevel {
    Debug, Info, Warn, Error,
}

actual object EchoFlowLogger {
    actual fun d(tag: String, message: String, throwable: Throwable?) =
        printLog(LogLevel.Debug, tag, message, throwable)

    actual fun i(tag: String, message: String, throwable: Throwable?) =
        printLog(LogLevel.Info, tag, message, throwable)

    actual fun w(tag: String, message: String, throwable: Throwable?) =
        printLog(LogLevel.Warn, tag, message, throwable)

    actual fun e(tag: String, message: String, throwable: Throwable?) =
        printLog(LogLevel.Error, tag, message, throwable)

    private fun printLog(level: LogLevel, tag: String, message: String, throwable: Throwable?) {
        println("[${level.name.uppercase()}] $tag: $message")
        throwable?.printStackTrace()
    }
}