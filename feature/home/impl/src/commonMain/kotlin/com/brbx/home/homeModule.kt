package com.brbx.home

import org.koin.dsl.module

val homeModule = module {
    includes(
        homeFeatureModule,
    )
}