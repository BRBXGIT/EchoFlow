package com.brbx.debug.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_NO
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewWrapper
import androidx.compose.ui.tooling.preview.PreviewWrapperProvider
import com.brbx.design_system.theme.EchoFlowTheme

@Preview(uiMode = UI_MODE_NIGHT_NO, name = "Light theme", showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, name = "Dark theme", showBackground = true)
@PreviewWrapper(ThemeWrapper::class)
annotation class EchoFlowPreview

private class ThemeWrapper : PreviewWrapperProvider {
    @Composable
    override fun Wrap(content: @Composable (() -> Unit)) {
        EchoFlowTheme(content = content)
    }
}