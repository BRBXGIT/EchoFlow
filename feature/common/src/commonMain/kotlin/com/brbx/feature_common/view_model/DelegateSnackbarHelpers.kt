package com.brbx.feature_common.view_model

import androidx.compose.material3.SnackbarDuration
import com.brbx.domain.model.enums.RequestException
import com.brbx.feature_common.utils.CommonText
import com.brbx.feature_common.utils.asRes
import com.brbx.mvi_core.helpers.postEffect

fun EchoFlowMviDelegate<*, *, *>.sendRetrySnackbar(
    e: RequestException,
    duration: SnackbarDuration = SnackbarDuration.Indefinite,
    dismissable: Boolean = false,
    callback: suspend () -> Unit,
) =
    postEffect(
        EchoFlowEffect.Snackbar(
            dismissable = dismissable,
            text = CommonText.Res(value = e.asRes()),
            duration = duration,
            action = EchoFlowEffect.Snackbar.Action(
                text = CommonText.Raw(value = "Retry"),
                onClick = callback,
            ),
        )
    )