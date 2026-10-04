package com.brbx.home.view_model

import com.brbx.domain.model.common.Track
import com.brbx.domain.model.common.onSuccess
import com.brbx.domain.pagination.Paginator
import com.brbx.domain.use_case.GetUserRecentlyTracksUseCase
import com.brbx.domain.use_case.GetUserRelatedTracksUseCase
import com.brbx.feature_common.model.toUi
import com.brbx.feature_common.view_model.onExceptionWithSnackbar
import com.brbx.feature_common.view_model.sendRetrySnackbar
import com.brbx.home.model.HomeIntent
import com.brbx.mvi_core.helpers.bindLatest
import com.brbx.mvi_core.helpers.launchAction
import com.brbx.mvi_core.helpers.reduce
import kotlinx.coroutines.CoroutineDispatcher

internal interface FeedLoader : HomeViewModelDelegate<HomeIntent.Refresh>

internal class FeedLoaderImpl(
    override val mviScope: HomeMviScope,
    private val tracksStore: HomeTracksStore,
    private val userRelatedTracksUseCase: GetUserRelatedTracksUseCase,
    private val userRecentlyTracksUseCase: GetUserRecentlyTracksUseCase,
    private val dispatcherDefault: CoroutineDispatcher,
) : FeedLoader {
    private var relatedPaginator: Paginator<Track>? = null

    init { loadFeed() }

    override fun invoke(intent: HomeIntent.Refresh) {
        refreshFeed()
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
        reduce { copy(recentlyListened = recentlyListened.copy(isLoading = true)) }
        userRecentlyTracksUseCase() onSuccess { tracks ->
            tracksStore.recent = tracks.collection
            reduce { copy(recentlyListened = tracks.toUi()) }
            reduce { copy(recentlyListened = recentlyListened.copy(isLoading = false)) }
        } onExceptionWithSnackbar {
            loadRecently()
        }
    }

    private suspend fun loadRelated() {
        relatedPaginator = userRelatedTracksUseCase()
        relatedPaginator?.state?.bindLatest(context = dispatcherDefault) { paged ->
            if (paged.exception != null) {
                sendRetrySnackbar(paged.exception!!) { loadRelated() }
                this
            } else {
                tracksStore.relatedToRecent = relatedPaginator
                copy(relatedToRecently = paged.toUi())
            }
        }
    }
}
