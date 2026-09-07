package com.brbx.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.brbx.data.handler.NetworkResponseHandler
import com.brbx.domain.model.util.fold
import com.brbx.network.model.PaginatedTracksResponseDto
import com.brbx.network.model.TrackDto

internal class TracksPagingSource(
    private val call: suspend (nextHref: String?) -> PaginatedTracksResponseDto,
    private val handler: NetworkResponseHandler,
) : PagingSource<String, TrackDto>() {
    override suspend fun load(params: LoadParams<String>): LoadResult<String, TrackDto> =
        handler.handle { call(params.key) }
            .fold(
                onSuccess = { dto ->
                    LoadResult.Page(
                        data = dto.collection,
                        prevKey = null,
                        nextKey = dto.nextHref,
                    )
                },
                onException = { e -> LoadResult.Error(throwable = e.toPagingException()) }
            )

    override fun getRefreshKey(state: PagingState<String, TrackDto>): String? = null
}
