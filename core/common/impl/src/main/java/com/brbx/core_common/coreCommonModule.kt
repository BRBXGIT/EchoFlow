package com.brbx.core_common

import com.brbx.core_common.dispatchers.dispatchersModule
import org.koin.dsl.module

val coreCommonModule = module {
    includes(dispatchersModule)
}