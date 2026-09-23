package com.brbx.home.composable

import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable

// No topbar for desktop
@Composable
internal actual fun HomeTopBar(scrollBehavior: TopAppBarScrollBehavior) {}