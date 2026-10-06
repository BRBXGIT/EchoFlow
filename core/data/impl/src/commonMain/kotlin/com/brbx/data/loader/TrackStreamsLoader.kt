package com.brbx.data.loader

import com.brbx.data.handler.NetworkResponseHandler
import com.brbx.domain.model.common.RequestResult
import com.brbx.domain.model.common.failure
import com.brbx.domain.model.common.fold
import com.brbx.domain.model.common.success
import com.brbx.domain.model.enums.RequestException
import com.brbx.network.api.TrackApi

internal interface TrackStreamsLoader {
    suspend fun load(urn: String): RequestResult<String>
}

internal class TrackStreamsLoaderImpl(
    private val trackApi: TrackApi,
    private val handler: NetworkResponseHandler,
) : TrackStreamsLoader {
    override suspend fun load(urn: String): RequestResult<String> =
        handler.handle {
            val streams = trackApi.getTrackStreams(urn)
            streams.hlsAac160
                ?: streams.hlsMp3128
                ?: streams.hlsPreviewMp3128
        }.fold(
            onException = { failure(exception = it) },
            onSuccess = { url ->
                url?.let { success(value = url) }
                    ?: failure(exception = RequestException.Unknown)
            },
        )
}