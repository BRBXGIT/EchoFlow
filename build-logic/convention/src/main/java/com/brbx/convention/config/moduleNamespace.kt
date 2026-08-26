package com.brbx.convention.config

import com.brbx.convention.constants.EchoFlow
import org.gradle.api.Project

internal val Project.moduleNamespace: String
    get() {
        val modulePath = path
            .split(":")
            .filter { it.isNotEmpty() }
            .joinToString(".") { it.replace("-", "_") }

        return if (modulePath.isNotEmpty()) {
            "${EchoFlow.Domain}.$modulePath"
        } else {
            EchoFlow.Domain
        }
    }