package com.brbx.data.provider

import android.content.Context
import androidx.annotation.OptIn
import androidx.core.net.toUri
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.ResolvingDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.brbx.data.common.NetworkConstants
import com.brbx.data.controller.echoFlowTrackScheme
import com.brbx.data.datasource.AuthTokenDataSource
import com.brbx.data.loader.TrackStreamsLoader
import com.brbx.domain.model.common.onException
import com.brbx.domain.model.common.onSuccess
import com.brbx.domain.model.enums.RequestException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

internal interface PlayerProvider {
    fun getPlayer(onFailedUrlException: (RequestException) -> Unit): ExoPlayer
}

internal class PlayerProviderImpl(
    private val context: Context,
    private val tokensSource: AuthTokenDataSource,
    private val dispatcherIo: CoroutineDispatcher,
    private val dispatcherDefault: CoroutineDispatcher,
    private val streamsLoader: TrackStreamsLoader,
) : PlayerProvider {
    override fun getPlayer(onFailedUrlException: (RequestException) -> Unit) =
        ExoPlayer.Builder(context)
            .setMediaSourceFactory(dataSourceFactory(onFailedUrlException))
            .setAudioAttributes(audioAttributes(), true)
            .setHandleAudioBecomingNoisy(true)
            .build()

    private fun audioAttributes() =
        AudioAttributes.Builder()
            .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
            .setUsage(C.USAGE_MEDIA)
            .build()

    @OptIn(UnstableApi::class)
    private fun dataSourceFactory(
        onFailedUrlException: (RequestException) -> Unit
    ): DefaultMediaSourceFactory {
        val access = runBlocking(context = dispatcherIo) {
            tokensSource.tokens.filterNotNull().first().access
        }
        val httpDataSourceFactory = DefaultHttpDataSource.Factory()
            .setDefaultRequestProperties(
                mapOf(NetworkConstants.AuthHeader to NetworkConstants.bearerAuth(token = access))
            )
        val resolver = ResolvingDataSource.Resolver { dataSpec ->
            val uri = dataSpec.uri
            if (uri.scheme == echoFlowTrackScheme) {
                var url: String? = null
                val urn = uri.toString().substringAfter(delimiter = "://")
                runBlocking(context = dispatcherDefault) {
                    streamsLoader.load(urn) onSuccess { resolved ->
                        url = resolved
                    } onException { e -> onFailedUrlException(e) }
                }
                if (url != null) {
                    return@Resolver dataSpec.withUri(url.toUri())
                } else onFailedUrlException(RequestException.Unknown)
            }
            dataSpec
        }
        val resolvingDataSourceFactory = ResolvingDataSource.Factory(
            httpDataSourceFactory,
            resolver
        )
        return DefaultMediaSourceFactory(context)
            .setDataSourceFactory(resolvingDataSourceFactory)
    }
}