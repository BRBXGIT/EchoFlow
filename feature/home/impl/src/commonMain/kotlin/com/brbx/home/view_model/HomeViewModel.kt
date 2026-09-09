package com.brbx.home.view_model

import androidx.compose.runtime.Immutable
import com.brbx.feature_common.view_model.EchoFlowViewModel
import com.brbx.feature_common.view_model.injectDelegate
import com.brbx.home.model.HomeIntent
import com.brbx.home.model.HomeState

@Immutable
internal class HomeViewModel : EchoFlowViewModel<HomeState, HomeIntent, Unit>(
    initialState = HomeState()
) {
    private val feedLoader by injectDelegate<FeedLoader>()

    override fun dispatchIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.Feed -> feedLoader(intent)
        }
    }
}