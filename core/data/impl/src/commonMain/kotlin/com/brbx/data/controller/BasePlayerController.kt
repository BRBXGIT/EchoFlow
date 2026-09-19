package com.brbx.data.controller

import com.brbx.domain.model.PlayerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal abstract class BasePlayerController : PlayerController {
    private val _state = MutableStateFlow(value = PlayerState())
    final override val playerState = _state.asStateFlow()

    protected fun reduce(transform: PlayerState.() -> PlayerState) =
        _state.update(function = transform)
}