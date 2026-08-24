package com.brbx.design_system.components.utils

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun rememberSnackbarHost(): SnackbarHostState =
    remember { SnackbarHostState() }