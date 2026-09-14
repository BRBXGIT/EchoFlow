package com.brbx.design_system.components.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import com.brbx.design_system.theme.mShapes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EchoFlowSheet(
    modifier: Modifier = Modifier,
    shape: Shape = mShapes.extraLarge,
    onDismissRequest: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) =
    ModalBottomSheet(
        modifier = modifier,
        shape = shape,
        onDismissRequest = onDismissRequest,
        content = content,
    )