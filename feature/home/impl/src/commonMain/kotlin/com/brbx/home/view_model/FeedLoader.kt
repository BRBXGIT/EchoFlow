package com.brbx.home.view_model

import com.brbx.domain.model.common.Track
import com.brbx.domain.model.common.onException
import com.brbx.domain.model.common.onSuccess
import com.brbx.domain.pagination.Paginator
import com.brbx.domain.use_case.GetUserRecentlyTracksUseCase
import com.brbx.domain.use_case.GetUserRelatedTracksUseCase
import com.brbx.feature_common.model.toUi
import com.brbx.feature_common.view_model.sendRetrySnackbar
import com.brbx.home.model.HomeIntent
import com.brbx.mvi_core.helpers.bindLatest
import com.brbx.mvi_core.helpers.launchAction
import com.brbx.mvi_core.helpers.reduce
import kotlinx.coroutines.CoroutineDispatcher

internal interface FeedLoader : HomeViewModelDelegate<HomeIntent.Feed>

internal class FeedLoaderImpl(
    override val scope: HomeMviScope,
    private val dispatcherDefault: CoroutineDispatcher,
    private val userRelatedTracksUseCase: GetUserRelatedTracksUseCase,
    private val userRecentlyTracksUseCase: GetUserRecentlyTracksUseCase,
) : FeedLoader {
    private var relatedPaginator: Paginator<Track>? = null

    override fun invoke(intent: HomeIntent.Feed) {
        when (intent) {
            HomeIntent.Feed.Load -> loadFeed()
            HomeIntent.Feed.Refresh -> refreshFeed()
        }
    }

    private fun refreshFeed() = launchAction(context = dispatcherDefault) {
        loadRecently()
        relatedPaginator?.refresh()
    }

    private fun loadFeed() = launchAction(context = dispatcherDefault) {
        loadRecently()
        loadRelated()
    }

    private suspend fun loadRecently() {
        userRecentlyTracksUseCase() onSuccess { tracks ->
            reduce { copy(recentlyListened = tracks.toUi()) }
        } onException { e ->
            sendRetrySnackbar(e) { loadRecently() }
        }
    }

    private suspend fun loadRelated() {
        relatedPaginator = userRelatedTracksUseCase()
        relatedPaginator?.state?.bindLatest(context = dispatcherDefault) { paged ->
            if (paged.exception != null) {
                sendRetrySnackbar(paged.exception!!) { loadRelated() }
                this
            } else {
                copy(relatedToRecently = paged.toUi())
            }
        }
    }
}
