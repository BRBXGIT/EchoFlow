package com.brbx.player.model

internal sealed interface PlayerIntent {
    sealed interface Playback : PlayerIntent {
        data object Pause : Playback
        data object Resume : Playback
    }

    sealed interface Seek : PlayerIntent {
        @JvmInline value class SeekTo(val positionMs: Long) : Seek
        data object SkipToNext : Seek
        data object SkipToPrevious : Seek
    }
}