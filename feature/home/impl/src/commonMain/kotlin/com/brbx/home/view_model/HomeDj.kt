package com.brbx.home.view_model

import com.brbx.domain.use_case.AddToPlaybackQueueUseCase
import com.brbx.domain.use_case.SetPlaybackQueueUseCase
import com.brbx.home.model.HomeIntent

internal interface HomeDj : HomeViewModelDelegate<HomeIntent.Player>

internal class HomeDjImpl(
    override val mviScope: HomeMviScope,
    private val tracksStore: HomeTracksStore,
    private val addToQueueUseCase: AddToPlaybackQueueUseCase,
    private val setQueueUseCase: SetPlaybackQueueUseCase,
) : HomeDj {
    override fun invoke(intent: HomeIntent.Player) {
        when (intent) {
            is HomeIntent.Player.AddToQueue -> addToQueue(intent.trackId)
            is HomeIntent.Player.PlayMix -> playMix(intent.trackIndex)
            is HomeIntent.Player.PlayRecent -> playRecent(intent.trackIndex)
        }
    }

    private fun playMix(index: Int) =
        setQueueUseCase(tracksStore.relatedToRecent, startIndex = index)

    private fun playRecent(index: Int) =
        setQueueUseCase(tracksStore.recent, startIndex = index)

    private fun addToQueue(id: Long) =
        addToQueueUseCase(tracksStore.findTrack(id))
}