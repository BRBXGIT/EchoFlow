package com.brbx.design_system.components.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.brbx.design_system.theme.mDimens
import com.brbx.design_system.theme.mTypography

@Composable
fun ButtonWithIcon(
    onClick: () -> Unit,
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier,
    iconSize: Dp = 24.dp,
    shape: Shape = CircleShape,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    arrangement: Arrangement.Horizontal = Arrangement.spacedBy(mDimens.micro4),
) =
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = colors,
        shape = shape,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = arrangement
        ) {
            EchoFlowIcon(imageVector = icon, modifier = Modifier.size(iconSize))

            Text(
                text = text,
                style = mTypography.bodyMedium,
            )
        }
    }