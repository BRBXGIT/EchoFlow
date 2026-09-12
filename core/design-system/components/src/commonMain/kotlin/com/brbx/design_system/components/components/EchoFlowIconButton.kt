package com.brbx.design_system.components.components

import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun EchoFlowIconButton(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    onClick: () -> Unit,
) =
    IconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
    ) {
        EchoFlowIcon(imageVector = imageVector)
    }

@Composable
fun EchoFlowFilledIconButton(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.filledIconButtonColors(),
    onClick: () -> Unit,
) =
    FilledIconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
    ) {
        EchoFlowIcon(imageVector = imageVector)
    }

@Composable
fun EchoFlowFilledTonalIconButton(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.filledTonalIconButtonColors(),
    onClick: () -> Unit,
) =
    FilledTonalIconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
    ) {
        EchoFlowIcon(imageVector = imageVector)
    }