package com.brbx.playlist.di

import org.koin.dsl.module

val playlistModule = module {
    includes(viewModelModule, playlistFeatureModule)
}