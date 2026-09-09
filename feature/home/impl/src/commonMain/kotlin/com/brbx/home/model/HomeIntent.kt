package com.brbx.home.model

internal sealed interface HomeIntent {
    sealed interface Feed : HomeIntent {
        data object Load : Feed
        data object Refresh : Feed
    }
}