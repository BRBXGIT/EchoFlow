package com.brbx.home.composable

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import com.brbx.debug.compose.EchoFlowPreview
import com.brbx.design_system.components.components.EchoFlowIconButton
import dev.chiksmedina.solar.BrokenSolar
import dev.chiksmedina.solar.broken.NetworkItProgramming
import dev.chiksmedina.solar.broken.networkitprogramming.Screencast

@Composable
internal fun HomeTopBar(scrollBehavior: TopAppBarScrollBehavior) =
    TopAppBar(
        title = {},
        scrollBehavior = scrollBehavior,
        actions = {
            EchoFlowIconButton(
                imageVector = BrokenSolar.NetworkItProgramming.Screencast,
                onClick = {}
            )
        }
    )

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@EchoFlowPreview
private fun HomeTopBarPreview() =
    HomeTopBar(
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    )
