package com.brbx.home.view_model

import com.brbx.home.model.HomeIntent
import com.brbx.mvi_core.helpers.reduce

internal interface TodayMixInteractor : HomeViewModelDelegate<HomeIntent.ToggleMixSheet>

internal class TodayMixInteractorImpl(
    override val scope: HomeMviScope,
) : TodayMixInteractor {
    override fun invoke(intent: HomeIntent.ToggleMixSheet) {
        reduce { copy(todayMixVisible = !todayMixVisible) }
    }
}