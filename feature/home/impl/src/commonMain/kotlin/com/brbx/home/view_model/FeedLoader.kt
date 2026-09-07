package com.brbx.home.view_model

import com.brbx.domain.model.util.onException
import com.brbx.domain.model.util.onSuccess
import com.brbx.feature_common.view_model.sendRetrySnackbar
import com.brbx.home.model.HomeIntent
import com.brbx.home.view_model.base.HomeMviScope
import com.brbx.home.view_model.base.HomeViewModelDelegate
import com.brbx.mvi_core.helpers.launchAction
import com.brbx.mvi_core.helpers.reduce
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineDispatcher

internal interface FeedLoader : HomeViewModelDelegate<HomeIntent.LoadFeed>

internal class FeedLoaderImpl(
    override val scope: HomeMviScope,
    private val dispatchedDefault: CoroutineDispatcher,
    private val recentlyPlayedSnapshot: GetRecentTracksUseCase,
) : FeedLoader {
    override fun invoke(intent: HomeIntent.LoadFeed) {
        launchAction(context = dispatchedDefault) {
            loadRecentlyPlayed()
        }
    }

    private fun loadRecentlyPlayed() {
        launchAction(context = dispatchedDefault) {
            recentlyPlayedSnapshot(count = 5)
                .onSuccess { tracks ->
                    reduce { copy(recentlyPlayed = tracks.toPersistentList()) }
                } onException { e ->
                    sendRetrySnackbar(e) { invoke(intent = HomeIntent.LoadFeed) }
                }
        }
    }
}
