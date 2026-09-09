package com.brbx.network.utils

import io.ktor.client.request.HttpRequestBuilder

internal fun HttpRequestBuilder.setupPaging() =
    url { parameters.append(name = "linked_partitioning", value = "1") }