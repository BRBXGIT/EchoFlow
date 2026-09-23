package com.brbx.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.brbx.design_system.theme.mMotion
import org.koin.compose.navigation3.koinEntryProvider
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun EchoFlowNavGraph() {
    val navigator = echoFlowNavigator
    val specs = enterSpec() togetherWith exitSpec()
    NavDisplay(
        sceneStrategies = listOf(rememberListDetailStrategy()),
        backStack = navigator.currentBackstack,
        modifier = Modifier.fillMaxSize(),
        onBack = { navigator.navigateBack() },
        entryProvider = koinEntryProvider(),
        transitionSpec = { specs },
        popTransitionSpec = { specs },
        predictivePopTransitionSpec = { specs },
        entryDecorators = listOf(rememberSaveableStateHolderNavEntryDecorator()),
    )
}

@Composable
private fun enterSpec() = fadeIn(animationSpec = mMotion.nonSpatialFastSpec()) +
        scaleIn(animationSpec = mMotion.mediumSpatialSpec(), initialScale = 0.92f)

@Composable
private fun exitSpec() = fadeOut(animationSpec = mMotion.nonSpatialFastSpec())

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun rememberListDetailStrategy(): ListDetailSceneStrategy<Any> {
    val windowAdaptiveInfo = currentWindowAdaptiveInfoV2()
    val directive = remember(key1 = windowAdaptiveInfo) {
        calculatePaneScaffoldDirective(windowAdaptiveInfo).copy(horizontalPartitionSpacerSize = 0.dp)
    }
    val strategy = rememberListDetailSceneStrategy<Any>(directive = directive)
    return strategy
}