package com.brbx.playlist.di

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import com.brbx.navigation.singleSerializer
import com.brbx.playlist.PlaylistRoute
import com.brbx.playlist.composable.PlaylistScaffold
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation

@OptIn(KoinExperimentalAPI::class, ExperimentalMaterial3AdaptiveApi::class)
internal val playlistFeatureModule = module {
    singleSerializer<PlaylistRoute> {
        subclass(PlaylistRoute::class, serializer = PlaylistRoute.serializer())
    }
    navigation<PlaylistRoute>(
        metadata = ListDetailSceneStrategy.detailPane()
    ) { route ->
        PlaylistScaffold(route.playlist)
    }
}