package com.brbx.navigation

import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import org.koin.compose.navigation3.koinEntryProvider
import org.koin.core.annotation.KoinExperimentalAPI
import soup.compose.material.motion.animation.materialFadeThroughIn
import soup.compose.material.motion.animation.materialFadeThroughOut

@OptIn(KoinExperimentalAPI::class)
@Composable
fun EchoFlowNavGraph() {
    val navigator = echoFlowNavigator
    NavDisplay(
        backStack = navigator.currentBackstack,
        modifier = Modifier.fillMaxSize(),
        onBack = { navigator.navigateBack() },
        entryProvider = koinEntryProvider(),
        transitionSpec = {
            materialFadeThroughIn() togetherWith materialFadeThroughOut()
        },
        popTransitionSpec = {
            materialFadeThroughIn() togetherWith materialFadeThroughOut()
        },
        predictivePopTransitionSpec = {
            materialFadeThroughIn() togetherWith materialFadeThroughOut()
        },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
        ),
    )
}