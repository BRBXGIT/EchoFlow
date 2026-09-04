package com.brbx.home.model

internal sealed interface HomeIntent {
    data object LoadFeed : HomeIntent
}