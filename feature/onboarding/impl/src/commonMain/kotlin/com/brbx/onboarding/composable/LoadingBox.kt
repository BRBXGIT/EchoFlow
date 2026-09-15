package com.brbx.onboarding.composable

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import com.brbx.debug.compose.EchoFlowPreview
import com.brbx.design_system.theme.mColors
import com.brbx.design_system.theme.mMotion

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun LoadingBox(
    loading: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(modifier = modifier) {
        content()

        val bgAlpha by animatedAlphaState(loading, target = 0.85f)
        if (loading) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .graphicsLayer { alpha = bgAlpha }
                    .background(color = mColors.surface)
                    .pointerInput(key1 = Unit) {
                        awaitPointerEventScope {

                        }
                    }
            )
        }

        val indicatorAlpha by animatedAlphaState(loading)
        if (loading) {
            ContainedLoadingIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .graphicsLayer { alpha = indicatorAlpha }
            )
        }
    }
}

@Composable
private fun animatedAlphaState(loading: Boolean, target: Float = 1f) =
    animateFloatAsState(
        targetValue = if (loading) target else 0f,
        animationSpec = mMotion.nonSpatialFastSpec(),
    )

@Composable
@EchoFlowPreview
private fun LoadingBoxPreview() =
    LoadingBox(
        loading = true,
        modifier = Modifier.fillMaxSize(),
    ) {
        Text("Content under loading")
    }
