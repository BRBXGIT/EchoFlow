package com.brbx.feature_common.utils

import com.brbx.domain.model.enums.RequestException
import echoflow.feature.common.generated.resources.Res
import echoflow.feature.common.generated.resources.error_conflict
import echoflow.feature.common.generated.resources.error_too_many_requests
import echoflow.feature.common.generated.resources.error_payload_too_large
import echoflow.feature.common.generated.resources.error_server_error
import echoflow.feature.common.generated.resources.error_unauthorized
import echoflow.feature.common.generated.resources.error_request_timeout
import echoflow.feature.common.generated.resources.error_internet
import echoflow.feature.common.generated.resources.error_unknown
import echoflow.feature.common.generated.resources.error_csrf_attack
import org.jetbrains.compose.resources.StringResource

fun RequestException.asRes(): StringResource =
    when (this) {
        RequestException.Conflict -> Res.string.error_conflict
        RequestException.TooManyRequests -> Res.string.error_too_many_requests
        RequestException.PayloadTooLarge -> Res.string.error_payload_too_large
        RequestException.ServerError -> Res.string.error_server_error
        RequestException.Unauthorized -> Res.string.error_unauthorized
        RequestException.RequestTimeout -> Res.string.error_request_timeout
        RequestException.Internet -> Res.string.error_internet
        RequestException.Unknown -> Res.string.error_unknown
        RequestException.CsrfAttack -> Res.string.error_csrf_attack
    }
