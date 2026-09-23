package com.brbx.home.di

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.brbx.design_system.theme.gFlexFontFamily
import com.brbx.design_system.theme.mColors
import com.brbx.design_system.theme.mTypography
import com.brbx.home.HomeRoute
import com.brbx.home.composable.HomeScaffold
import com.brbx.navigation.singleSerializer
import echoflow.feature.home.impl.generated.resources.Res
import echoflow.feature.home.impl.generated.resources.no_playlist_selected_label
import org.jetbrains.compose.resources.stringResource
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation

@OptIn(KoinExperimentalAPI::class, ExperimentalMaterial3AdaptiveApi::class)
internal val homeFeatureModule = module {
    singleSerializer<HomeRoute> {
        subclass(HomeRoute::class, serializer = HomeRoute.serializer())
    }
    navigation<HomeRoute>(
        metadata = ListDetailSceneStrategy.listPane { ListPlaceholder() }
    ) {
        HomeScaffold()
    }
}

@Composable
private fun ListPlaceholder() =
    Box(
        modifier = Modifier.fillMaxSize().background(color = mColors.surface),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(Res.string.no_playlist_selected_label),
            style = mTypography.titleLarge.copy(
                fontFamily = gFlexFontFamily(),
                color = mColors.onSurface,
                fontWeight = FontWeight.Bold,
            )
        )
    }