package com.brbx.home.view_model

import com.brbx.domain.model.onException
import com.brbx.domain.model.onSuccess
import com.brbx.domain.use_case.GetUserRecentlyPlayedUseCase
import com.brbx.feature_common.view_model.sendRetrySnackbar
import com.brbx.home.model.HomeIntent
import com.brbx.home.view_model.base.HomeMviScope
import com.brbx.home.view_model.base.HomeViewModelDelegate
import com.brbx.mvi_core.helpers.launchAction
import com.brbx.mvi_core.helpers.reduce
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.coroutineScope

internal interface FeedLoader : HomeViewModelDelegate<HomeIntent.LoadFeed>

internal class FeedLoaderImpl(
    override val scope: HomeMviScope,
    private val recentlyPlayedUseCase: GetUserRecentlyPlayedUseCase,
    private val dispatchedIo: CoroutineDispatcher,
) : FeedLoader {
    override fun invoke(intent: HomeIntent.LoadFeed) {
        launchAction(context = dispatchedIo) {
            coroutineScope {
                loadRecentlyPlayed()
            }
        }
    }

    private suspend fun loadRecentlyPlayed() =
        recentlyPlayedUseCase().onSuccess { tracks ->
            reduce { copy(recentlyPlayed = tracks.toPersistentList()) }
        } onException { e ->
            sendRetrySnackbar(e) { invoke(intent = HomeIntent.LoadFeed) }
        }
}