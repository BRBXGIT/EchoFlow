package com.brbx.home.model

internal sealed interface HomeIntent {

    data object Refresh : HomeIntent

    sealed interface Playlists : HomeIntent {
        data object OpenMix : Playlists
        data object OpenRecent : Playlists
    }
}