package com.brbx.data.repository

import com.brbx.data.common.toDomain
import com.brbx.data.handler.NetworkResponseHandler
import com.brbx.data.pagination.FlowPaginator
import com.brbx.domain.model.common.Track
import com.brbx.domain.model.common.map
import com.brbx.domain.pagination.Paginator
import com.brbx.network.api.UserFeedApi
import com.brbx.network.model.base.PaginatedDto

internal class UserFeedRepositoryImpl(
    private val feedApi: UserFeedApi,
    private val handler: NetworkResponseHandler,
) : UserFeedRepository {
    override suspend fun getFeedPaginator(): Paginator<Track> =
        FlowPaginator(
            call = { next ->
                handler.handle { feedApi.getFeedTracks(url = next) }
                    .map { pagedFeed ->
                        PaginatedDto(
                            collection = pagedFeed.collection.mapNotNull { it.origin },
                            nextHref = pagedFeed.nextHref,
                        )
                    }
            },
            mapper = { it.toDomain() },
        )
}
