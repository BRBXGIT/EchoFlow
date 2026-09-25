package com.brbx.player.di

import org.koin.dsl.module

val playerModule = module {
    includes(viewModelModule)
}