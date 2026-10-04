package com.brbx.network.utils

import io.ktor.http.Parameters

internal fun parametersOfNotNull(vararg pairs: Pair<String, String?>): Parameters = Parameters.build {
    pairs.forEach { (key, value) ->
        if (value != null) append(name = key, value = value)
    }
}
