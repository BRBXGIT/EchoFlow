package com.brbx.feature_common.view_model

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Immutable
import com.brbx.feature_common.utils.CommonText

@Immutable
sealed interface EchoFlowEffect {
    data class Snackbar(
        val text: CommonText,
        val action: Action? = null,
        val duration: SnackbarDuration = SnackbarDuration.Short,
    ) : EchoFlowEffect {
        data class Action(
            val text: CommonText,
            val onClick: () -> Unit,
            val onDismiss: () -> Unit = {},
        )
    }

    object NavigateBack : EchoFlowEffect
}

