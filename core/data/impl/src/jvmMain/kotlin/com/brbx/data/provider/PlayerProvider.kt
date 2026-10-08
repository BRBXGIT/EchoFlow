package com.brbx.data.provider

import uk.co.caprica.vlcj.factory.MediaPlayerFactory
import uk.co.caprica.vlcj.factory.discovery.NativeDiscovery
import uk.co.caprica.vlcj.player.base.MediaPlayer
import java.io.File

internal interface PlayerProvider {
    fun getPlayer(): MediaPlayer
}

internal class PlayerProviderImpl : PlayerProvider {

    private val factory: MediaPlayerFactory by lazy {
        val libVlcDir = listOf(
            File("jvm-app/libvlc"),
            File("libvlc"),
        ).firstOrNull { it.exists() }

        if (libVlcDir != null) {
            val absolutePath = libVlcDir.absolutePath
            System.setProperty("jna.library.path", absolutePath)
            System.setProperty("VLC_PLUGIN_PATH", "$absolutePath/plugins")
        }

        val discovered = NativeDiscovery().discover()
        check(discovered) {
            "Can not load LibVLC DLL from directory: ${libVlcDir?.absolutePath ?: "jvm-app/libvlc"}"
        }

        MediaPlayerFactory(
            "--quiet",
            "--no-video-title-show",
        )
    }

    private var cached: MediaPlayer? = null

    override fun getPlayer(): MediaPlayer =
        cached ?: factory.mediaPlayers().newMediaPlayer().also { cached = it }
}
