package com.brbx.home.view_model

import com.brbx.home.model.HomeIntent
import com.brbx.mvi_core.helpers.reduce

internal interface SheetsInteractor : HomeViewModelDelegate<HomeIntent.Sheets>

internal class SheetsInteractorImpl(
    override val scope: HomeMviScope,
) : SheetsInteractor {
    override fun invoke(intent: HomeIntent.Sheets) {
        when (intent) {
            HomeIntent.Sheets.ToggleMixSheet -> toggleMixSheet()
            HomeIntent.Sheets.ToggleRecentSheet -> toggleRecentSheet()
        }
    }

    private fun toggleMixSheet() =
        reduce { copy(todayMixVisible = !todayMixVisible) }

    private fun toggleRecentSheet() =
        reduce { copy(recentSheetsVisible = !recentSheetsVisible) }
}