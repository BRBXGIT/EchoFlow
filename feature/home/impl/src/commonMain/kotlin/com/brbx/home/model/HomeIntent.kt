package com.brbx.home.model

internal sealed interface HomeIntent {

    data object Refresh : HomeIntent

    sealed interface Playlists : HomeIntent {
        data object OpenMix : Playlists
        data object OpenRecent : Playlists
    }

    sealed interface Player : HomeIntent {
        @JvmInline value class PlayMix(val trackIndex: Int) : Player
        @JvmInline value class PlayRecent(val trackIndex: Int) : Player
    }
}