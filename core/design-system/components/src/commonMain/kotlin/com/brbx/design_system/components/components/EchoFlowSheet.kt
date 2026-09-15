package com.brbx.design_system.components.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import com.brbx.design_system.theme.mColors
import com.brbx.design_system.theme.mMotion

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EchoFlowSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    content: @Composable ColumnScope.() -> Unit,
) =
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        content = content,
        contentWindowInsets = {
            BottomSheetDefaults.modalWindowInsets.only(
                sides = WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom,
            )
        },
        dragHandle = {
            EchoFlowSheetDragHandle(scrollBehavior = scrollBehavior)
        },
    )

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EchoFlowSheetDragHandle(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    unscrolledColor: Color = mColors.surfaceContainer.copy(alpha = 0f),
    scrolledColor: Color = mColors.surfaceContainer,
) {
    val isScrolled by remember(scrollBehavior) {
        derivedStateOf {
            scrollBehavior != null &&
                scrollBehavior.state.contentOffset < -0.01f
        }
    }

    val backgroundColor by animateColorAsState(
        targetValue = if (isScrolled) scrolledColor else unscrolledColor,
        animationSpec = mMotion.nonSpatialFastSpec(),
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .drawBehind { drawRect(backgroundColor) }
            .windowInsetsPadding(insets = WindowInsets.statusBars.only(sides = WindowInsetsSides.Top)),
    ) {
        BottomSheetDefaults.DragHandle()
    }
}
