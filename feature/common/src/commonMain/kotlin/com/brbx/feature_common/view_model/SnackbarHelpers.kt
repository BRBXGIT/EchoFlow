package com.brbx.feature_common.view_model

import androidx.compose.material3.SnackbarDuration
import com.brbx.domain.model.common.RequestResult
import com.brbx.domain.model.common.onException
import com.brbx.domain.model.enums.RequestException
import com.brbx.feature_common.utils.asCommonText
import com.brbx.feature_common.utils.asRes
import com.brbx.mvi_core.helpers.postEffect

fun EchoFlowIntentDelegate<*, *, *>.sendRetrySnackbar(
    e: RequestException,
    duration: SnackbarDuration = SnackbarDuration.Indefinite,
    dismissable: Boolean = false,
    callback: suspend () -> Unit,
) =
    postEffect(
        EchoFlowEffect.Snackbar(
            dismissable = dismissable,
            text = e.asRes().asCommonText(),
            duration = duration,
            action = EchoFlowEffect.Snackbar.Action(
                text = "Retry".asCommonText(),
                onClick = callback,
            ),
        )
    )

context(delegate: EchoFlowIntentDelegate<*, *, *>)
infix fun <T> RequestResult<T>.onExceptionWithSnackbar(
    onRetry: suspend () -> Unit,
): RequestResult<T> = onException { e ->
    delegate.sendRetrySnackbar(e, callback = onRetry)
}