package com.brbx.debug.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_NO
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewWrapper
import androidx.compose.ui.tooling.preview.PreviewWrapperProvider
import com.brbx.design_system.theme.EchoFlowTheme
import com.brbx.design_system.theme.mColors

@Preview(uiMode = UI_MODE_NIGHT_NO, name = "Light theme", showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, name = "Dark theme", showBackground = true)
@PreviewWrapper(ComponentThemeWrapper::class)
annotation class EchoFlowPreview

@Preview(uiMode = UI_MODE_NIGHT_NO, name = "Light theme", showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, name = "Dark theme", showBackground = true)
@PreviewWrapper(ScreenThemeWrapper::class)
annotation class EchoFlowScreenPreview

private class ComponentThemeWrapper : PreviewWrapperProvider {
    @Composable
    override fun Wrap(content: @Composable (() -> Unit)) {
        EchoFlowTheme {
            Box(
                modifier = Modifier.background(color = mColors.background)
            ) { content() }
        }
    }
}

private class ScreenThemeWrapper : PreviewWrapperProvider {
    @Composable
    override fun Wrap(content: @Composable (() -> Unit)) {
        EchoFlowTheme {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = mColors.background)
            ) { content() }
        }
    }
}
