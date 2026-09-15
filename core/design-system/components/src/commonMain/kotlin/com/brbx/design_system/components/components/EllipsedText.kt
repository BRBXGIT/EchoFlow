package com.brbx.design_system.components.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import com.brbx.debug.compose.EchoFlowPreview
import com.brbx.design_system.theme.mTypography

@Composable
fun EllipsedText(
    text: String,
    style: TextStyle,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) =
    Text(
        style = style,
        text = text,
        maxLines = maxLines,
        overflow = overflow,
    )

@Composable
@EchoFlowPreview
private fun EllipsedTextPreview() =
    EllipsedText(
        text = "Long text example that should be ellipsed when overflowed",
        style = mTypography.bodyMedium,
    )
