package com.brbx.home.di

import com.brbx.home.HomeRoute
import com.brbx.home.composable.HomeScaffold
import com.brbx.navigation.singleSerializer
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation

@OptIn(KoinExperimentalAPI::class)
internal val homeFeatureModule = module {
    singleSerializer<HomeRoute> {
        subclass(HomeRoute::class, serializer = HomeRoute.serializer())
    }
    navigation<HomeRoute> {
        HomeScaffold()
    }
}