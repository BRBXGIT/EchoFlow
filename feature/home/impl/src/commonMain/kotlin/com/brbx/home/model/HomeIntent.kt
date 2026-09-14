package com.brbx.home.model

internal sealed interface HomeIntent {

    data object Refresh : HomeIntent

    data object ToggleMixSheet : HomeIntent
}