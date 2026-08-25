package com.brbx.design_system.components.components

import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun EchoFlowIconButton(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
) =
    IconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
    ) {
        EchoFlowIcon(imageVector = imageVector)
    }