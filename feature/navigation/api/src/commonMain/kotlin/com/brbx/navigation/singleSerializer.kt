package com.brbx.navigation

import kotlinx.serialization.modules.PolymorphicModuleBuilder
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.core.module.Module
import org.koin.core.qualifier.named

inline fun <reified T : EchoFlowNavKey> Module.singleSerializer(
    crossinline builder: PolymorphicModuleBuilder<EchoFlowNavKey>.() -> Unit,
) {
    single(qualifier = named<T>()) {
        SerializersModule {
            polymorphic(
                baseClass = EchoFlowNavKey::class,
                builderAction = builder,
            )
        }
    }
}