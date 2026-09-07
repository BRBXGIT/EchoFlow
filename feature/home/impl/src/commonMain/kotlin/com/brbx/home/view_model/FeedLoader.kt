package com.brbx.home.view_model

import androidx.paging.cachedIn
import com.brbx.domain.model.UserFeed
import com.brbx.domain.model.util.onException
import com.brbx.domain.model.util.onSuccess
import com.brbx.domain.use_case.GetUserFeedUseCase
import com.brbx.feature_common.view_model.sendRetrySnackbar
import com.brbx.home.model.HomeIntent
import com.brbx.home.model.UiFeed
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
    private val feedUseCase: GetUserFeedUseCase,
) : FeedLoader {
    override fun invoke(intent: HomeIntent.LoadFeed) {
        launchAction(context = dispatchedDefault) {
            loadRecentlyPlayed()
        }
    }

    private fun loadRecentlyPlayed() {
        launchAction(context = dispatchedDefault) {
            feedUseCase().onSuccess { feed ->
                reduce { copy(feed = feed.toUi()) }
            } onException { e ->
                sendRetrySnackbar(e) { invoke(intent = HomeIntent.LoadFeed) }
            }
        }
    }

    private fun UserFeed.toUi(): UiFeed =
        UiFeed(
            recentPlayed = recentlyPlayed.toPersistentList(),
            relatedToRecent = relatedToRecently?.cachedIn(scope.viewModelScope),
        )
}
