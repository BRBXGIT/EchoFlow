package com.brbx.design_system.components.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun TextWithEllipsis(
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