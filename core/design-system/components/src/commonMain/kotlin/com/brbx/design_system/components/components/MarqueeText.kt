package com.brbx.design_system.components.components

import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun MarqueeText(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    fadeWidth: Dp = 14.dp,
    iterations: Int = Int.MAX_VALUE,
    style: TextStyle = LocalTextStyle.current,
) {
    val textMeasurer = rememberTextMeasurer()

    BoxWithConstraints(modifier = modifier) {
        val availableWidthPx = constraints.maxWidth

        val textLayoutResult = remember(text, style) {
            textMeasurer.measure(
                text = text,
                style = style,
                maxLines = 1,
            )
        }

        val isOverflowing = textLayoutResult.size.width > availableWidthPx
        val shouldMarquee = enabled && isOverflowing

        val textModifier = remember(key1 = shouldMarquee) {
            if (shouldMarquee) {
                Modifier
                    .fillMaxWidth()
                    .fadingEdge(fadeWidth = fadeWidth)
                    .basicMarquee(
                        iterations = iterations,
                        animationMode = MarqueeAnimationMode.Immediately,
                    )
            } else {
                Modifier.fillMaxWidth()
            }
        }

        Text(
            text = text,
            modifier = textModifier,
            style = style,
            maxLines = 1,
            overflow = if (shouldMarquee) TextOverflow.Clip else TextOverflow.Ellipsis,
            softWrap = false,
        )
    }
}

private fun Modifier.fadingEdge(fadeWidth: Dp): Modifier = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()

        val fadePx = fadeWidth.toPx()
        if (fadePx <= 0f || size.width <= 0f) return@drawWithContent

        val fadeFraction = (fadePx / size.width).coerceIn(0f, 0.45f)

        val fadeBrush = Brush.horizontalGradient(
            0f to Color.Transparent,
            fadeFraction to Color.Black,
            (1f - fadeFraction) to Color.Black,
            1f to Color.Transparent,
        )

        drawRect(
            brush = fadeBrush,
            blendMode = BlendMode.DstIn,
        )
    }