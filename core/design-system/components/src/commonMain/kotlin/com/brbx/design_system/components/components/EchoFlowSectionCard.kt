package com.brbx.design_system.components.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import com.brbx.design_system.theme.mColors
import com.brbx.design_system.theme.mDimens
import com.brbx.design_system.theme.mShapes

@Composable
fun EchoFlowSectionCard(
    modifier: Modifier = Modifier,
    shape: Shape = mShapes.extraLarge,
    colors: CardColors = CardDefaults.cardColors(containerColor = mColors.surfaceContainerLow),
    verticalPadding: Dp = mDimens.micro6,
    content: @Composable ColumnScope.() -> Unit,
) =
    Card(
        shape = shape,
        colors = colors,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .then(if (verticalPadding > mDimens.zero) Modifier.padding(vertical = verticalPadding) else Modifier),
            content = content,
        )
    }
