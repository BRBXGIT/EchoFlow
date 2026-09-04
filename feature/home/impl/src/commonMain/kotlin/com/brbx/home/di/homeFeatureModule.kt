package com.brbx.home.di

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.brbx.design_system.theme.mTypography
import com.brbx.home.HomeRoute
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
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = "HOME",
                style = mTypography.headlineLarge
            )
        }
    }
}