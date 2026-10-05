package com.brbx.data.provider

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.brbx.data.common.NetworkConstants
import com.brbx.data.datasource.AuthTokenDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

internal interface PlayerProvider {
    suspend fun getPlayer(): ExoPlayer
}

internal class PlayerProviderImpl(
    private val context: Context,
    private val tokensSource: AuthTokenDataSource,
    private val dispatcherIo: CoroutineDispatcher,
) : PlayerProvider {
    override suspend fun getPlayer() =
        ExoPlayer.Builder(context)
            .setMediaSourceFactory(dataSourceFactory())
            .setAudioAttributes(audioAttributes(), true)
            .setHandleAudioBecomingNoisy(true)
            .build()

    private fun audioAttributes() =
        AudioAttributes.Builder()
            .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
            .setUsage(C.USAGE_MEDIA)
            .build()

    @OptIn(UnstableApi::class)
    private suspend fun dataSourceFactory(): DefaultMediaSourceFactory {
        val access = withContext(context = dispatcherIo) {
            tokensSource.tokens.filterNotNull().first().access
        }
        val httpDataSourceFactory = DefaultHttpDataSource.Factory()
            .setDefaultRequestProperties(
                mapOf(NetworkConstants.AuthHeader to NetworkConstants.bearerAuth(token = access))
            )
        return DefaultMediaSourceFactory(context)
            .setDataSourceFactory(httpDataSourceFactory)
    }
}