package com.brbx.data.paging

import com.brbx.domain.model.enums.RequestException
import com.brbx.domain.model.utils.PagingException
import com.brbx.domain.model.utils.PagingException.*

internal fun RequestException.toPagingException(): PagingException =
    when (this) {
        RequestException.Conflict -> Conflict()
        RequestException.TooManyRequests -> TooManyRequests()
        RequestException.PayloadTooLarge -> PayloadTooLarge()
        RequestException.ServerError -> ServerError()
        RequestException.Unauthorized -> Unauthorized()
        RequestException.RequestTimeout -> RequestTimeout()
        RequestException.Internet -> Internet()
        RequestException.Unknown -> Unknown()
        RequestException.CsrfAttack -> CsrfAttack()
    }