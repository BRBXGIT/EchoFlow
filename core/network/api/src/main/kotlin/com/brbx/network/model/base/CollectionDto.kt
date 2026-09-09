package com.brbx.network.model.base

import kotlinx.serialization.Serializable

@Serializable
data class CollectionDto<T>(
    val collection: List<T>
)
