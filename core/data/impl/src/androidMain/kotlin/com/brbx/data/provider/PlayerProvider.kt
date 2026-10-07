package com.brbx.data.provider

import android.content.Context
import androidx.annotation.OptIn
import androidx.core.net.toUri
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.ResolvingDataSource
import androidx.media3.datasource.okhttp.OkHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.brbx.data.common.NetworkConstants
import com.brbx.data.controller.echoFlowTrackScheme
import com.brbx.data.datasource.AuthTokenDataSource
import com.brbx.data.loader.TrackStreamsLoader
import com.brbx.domain.model.common.onException
import com.brbx.domain.model.common.onSuccess
import com.brbx.domain.model.enums.RequestException
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient

internal interface PlayerProvider {
    fun getPlayer(onFailedUrlException: (RequestException) -> Unit): ExoPlayer
}

internal class PlayerProviderImpl(
    private val context: Context,
    private val tokensSource: AuthTokenDataSource,
    private val streamsLoader: TrackStreamsLoader,
) : PlayerProvider {

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val token = runBlocking {
                    tokensSource.tokens.filterNotNull().first().access
                }
                val request = chain.request().newBuilder()
                    .header(NetworkConstants.AuthHeader, NetworkConstants.bearerAuth(token))
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    override fun getPlayer(onFailedUrlException: (RequestException) -> Unit): ExoPlayer =
        ExoPlayer.Builder(context)
            .setMediaSourceFactory(dataSourceFactory(onFailedUrlException))
            .setAudioAttributes(audioAttributes(), true)
            .setHandleAudioBecomingNoisy(true)
            .build()

    private fun audioAttributes() = AudioAttributes.Builder()
        .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
        .setUsage(C.USAGE_MEDIA)
        .build()

    @OptIn(UnstableApi::class)
    private fun dataSourceFactory(
        onFailedUrlException: (RequestException) -> Unit,
    ): DefaultMediaSourceFactory {
        val httpDataSourceFactory = OkHttpDataSource.Factory(okHttpClient)
        val resolver = ResolvingDataSource.Resolver { dataSpec ->
            val uri = dataSpec.uri
            if (uri.scheme == echoFlowTrackScheme) {
                var hlsUrl: String? = null
                val urn = uri.toString().substringAfter("://")
                runBlocking {
                    streamsLoader.load(urn)
                        .onSuccess { url -> hlsUrl = url }
                        .onException { e -> onFailedUrlException(e) }
                }
                return@Resolver if (hlsUrl != null) {
                    dataSpec.withUri(hlsUrl.toUri())
                } else {
                    onFailedUrlException(RequestException.Unknown)
                    dataSpec
                }
            }
            dataSpec
        }
        return DefaultMediaSourceFactory(context)
            .setDataSourceFactory(ResolvingDataSource.Factory(httpDataSourceFactory, resolver))
    }
}