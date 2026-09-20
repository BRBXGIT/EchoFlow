package com.brbx.playlist.di

import com.brbx.navigation.singleSerializer
import com.brbx.playlist.PlaylistRoute
import com.brbx.playlist.composable.PlaylistScaffold
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation

@OptIn(KoinExperimentalAPI::class)
internal val playlistFeatureModule = module {
    singleSerializer<PlaylistRoute> {
        subclass(PlaylistRoute::class, serializer = PlaylistRoute.serializer())
    }
    navigation<PlaylistRoute> { route ->
        PlaylistScaffold(route.playlist)
    }
}