package com.brbx.echoflow

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Text
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowScope
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.brbx.common_app.EchoFlowApp
import com.brbx.common_app.setupKoin
import com.brbx.design_system.components.components.EchoFlowIconButton
import com.brbx.design_system.theme.EchoFlowTheme
import com.brbx.design_system.theme.gFlexFontFamily
import com.brbx.design_system.theme.mColors
import com.brbx.design_system.theme.mDimens
import com.brbx.design_system.theme.mShapes
import com.brbx.design_system.theme.mTypography
import com.brbx.jvm_app.TopLevelConfig
import dev.chiksmedina.solar.BrokenSolar
import dev.chiksmedina.solar.broken.DesignTools
import dev.chiksmedina.solar.broken.EssentionalUi
import dev.chiksmedina.solar.broken.designtools.CropMinimalistic
import dev.chiksmedina.solar.broken.essentionalui.CloseCircle
import dev.chiksmedina.solar.broken.essentionalui.MinusCircle

fun main() = application {
    setupKoin()

    EchoFlowTheme {
        EchoFlowWindow(
            onCloseRequest = ::exitApplication
        )
    }
}

@Composable
private fun EchoFlowWindow(
    onCloseRequest: () -> Unit,
) {
    val windowState = rememberWindowState(
        size = DpSize(width = 1024.dp, height = 800.dp),
        position = WindowPosition(alignment = Alignment.Center)
    )

    Window(
        onCloseRequest = onCloseRequest,
        state = windowState,
        undecorated = true,
        transparent = true,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = mShapes.small)
                .background(color = mColors.background)
                .border(width = 1.dp, color = mColors.outlineVariant, shape = mShapes.small)
        ) {
            JvmHeader(
                windowState = windowState,
                onClose = onCloseRequest,
                modifier = Modifier.fillMaxWidth()
            )

            ContentContainer {
                EchoFlowApp(deeplink = null)
            }
        }
    }
}

@Composable
private fun WindowScope.JvmHeader(
    windowState: WindowState,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) =
    JvmHeaderContainer(modifier) {
        JvmHeaderContent(
            onMinimize = { windowState.isMinimized = true },
            onTogglePlacement = { windowState.togglePlacement() },
            onClose = onClose
        )
    }

@Composable
private fun WindowScope.JvmHeaderContainer(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) =
    WindowDraggableArea(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = mColors.surfaceContainer)
                .padding(vertical = mDimens.micro1, horizontal = mDimens.micro4),
            content = content
        )
    }

@Composable
private fun JvmHeaderContent(
    title: String = TopLevelConfig.appName,
    onMinimize: () -> Unit,
    onTogglePlacement: () -> Unit,
    onClose: () -> Unit
) {
    Text(
        text = title,
        style = mTypography.titleMedium.copy(
            fontFamily = gFlexFontFamily(),
            color = mColors.onSurface,
            fontWeight = FontWeight.Bold,
        )
    )

    Row(verticalAlignment = Alignment.CenterVertically) {
        val iconSize = mDimens.macro1
        EchoFlowIconButton(
            iconSize = iconSize,
            onClick = onMinimize,
            colors = iconButtonColors(),
            imageVector = BrokenSolar.EssentionalUi.MinusCircle,
        )

        EchoFlowIconButton(
            iconSize = iconSize,
            onClick = onTogglePlacement,
            colors = iconButtonColors(),
            imageVector = BrokenSolar.DesignTools.CropMinimalistic,
        )

        CloseWindowButton(onClick = onClose, iconSize)
    }
}

@Composable
private fun CloseWindowButton(
    onClick: () -> Unit,
    iconSize: Dp,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    EchoFlowIconButton(
        onClick = onClick,
        iconSize = iconSize,
        modifier = modifier,
        imageVector = BrokenSolar.EssentionalUi.CloseCircle,
        interactionSource = interactionSource,
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = if (isHovered) mColors.onError else mColors.onSurface,
            containerColor = if (isHovered) mColors.error else Color.Transparent
        )
    )
}

@Composable
private fun ColumnScope.ContentContainer(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) =
    Box(
        modifier = modifier.weight(1f),
        content = content
    )

@Composable
private fun iconButtonColors(): IconButtonColors =
    IconButtonDefaults.iconButtonColors(contentColor = mColors.onSurface)

private fun WindowState.togglePlacement() {
    placement = if (placement == WindowPlacement.Maximized) {
        WindowPlacement.Floating
    } else {
        WindowPlacement.Maximized
    }
}