package com.brbx.network.api

import com.brbx.network.client.ApiClientProvider
import com.brbx.network.model.TracksStreamsDto
import io.ktor.client.call.body
import io.ktor.client.request.get

internal class TrackApiImpl(
    private val clientProvider: ApiClientProvider,
) : TrackApi {
    override suspend fun getTrackStreams(urn: String): TracksStreamsDto =
        clientProvider.client.get(urlString = trackStreamsEndPoint(urn)).body()

    private companion object {
        fun trackStreamsEndPoint(urn: String) = "/tracks/$urn/streams"
    }
}