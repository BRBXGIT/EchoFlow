package com.brbx.home.di

import org.koin.dsl.module

val homeModule = module {
    includes(
        homeFeatureModule,
        viewModelModule,
    )
}