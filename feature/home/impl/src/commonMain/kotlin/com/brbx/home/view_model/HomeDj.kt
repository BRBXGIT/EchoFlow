package com.brbx.home.view_model

import com.brbx.domain.use_case.SetPlaybackQueueUseCase
import com.brbx.home.model.HomeIntent

internal interface HomeDj : HomeViewModelDelegate<HomeIntent.Player>

internal class HomeDjImpl(
    override val mviScope: HomeMviScope,
    private val tracksStore: HomeTracksStore,
    private val setQueueUseCase: SetPlaybackQueueUseCase,
) : HomeDj {
    override fun invoke(intent: HomeIntent.Player) {
        when (intent) {
            is HomeIntent.Player.PlayMix -> playMix(intent.trackIndex)
            is HomeIntent.Player.PlayRecent -> playRecent(intent.trackIndex)
        }
    }

    private fun playMix(index: Int) =
        tracksStore.dailyMix?.let { setQueueUseCase(paginator = it, startIndex = index) }

    private fun playRecent(index: Int) =
        setQueueUseCase(tracksStore.recent, startIndex = index)
}
