package com.brbx.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.modules.PolymorphicModuleBuilder
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.core.module.Module
import org.koin.core.qualifier.named

inline fun <reified T : EchoFlowNavKey> Module.singleSerializer(
    crossinline builder: PolymorphicModuleBuilder<NavKey>.() -> Unit,
) {
    single(qualifier = named<T>()) {
        SerializersModule {
            polymorphic(
                baseClass = NavKey::class,
                builderAction = builder,
            )
        }
    }
}