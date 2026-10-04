package com.brbx.data.provider

import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.exoplayer.ExoPlayer

internal interface PlayerProvider {
    val player: ExoPlayer
}

internal class PlayerProviderImpl(
    private val context: Context,
) : PlayerProvider {
    override val player by lazy {
        ExoPlayer.Builder(context)
            .setAudioAttributes(audioAttributes(), true)
            .setHandleAudioBecomingNoisy(true)
            .build()
    }

    private fun audioAttributes() =
        AudioAttributes.Builder()
            .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
            .setUsage(C.USAGE_MEDIA)
            .build()
}