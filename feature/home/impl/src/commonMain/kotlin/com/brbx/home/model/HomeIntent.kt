package com.brbx.home.model

internal sealed interface HomeIntent {

    data object Refresh : HomeIntent

    sealed interface Sheets : HomeIntent {
        data object ToggleMixSheet : Sheets

        data object ToggleRecentSheet : Sheets
    }
}