package com.brbx.feature_common.view_model

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Immutable
import com.brbx.feature_common.utils.CommonText
import com.brbx.navigation.EchoFlowNavKey

@Immutable
sealed interface EchoFlowEffect {
    data object NavigateBack : EchoFlowEffect

    data object DropPreviousNavKey : EchoFlowEffect

    @JvmInline value class Navigate(val key: EchoFlowNavKey) : EchoFlowEffect

    @JvmInline value class OpenLink(val link: String) : EchoFlowEffect

    data class Snackbar(
        val text: CommonText,
        val dismissable: Boolean = false,
        val action: Action? = null,
        val duration: SnackbarDuration = SnackbarDuration.Short,
    ) : EchoFlowEffect {
        data class Action(
            val text: CommonText,
            val onClick: suspend () -> Unit,
            val onDismiss: suspend () -> Unit = {},
        )
    }
}

