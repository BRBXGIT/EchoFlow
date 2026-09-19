package com.brbx.data.controller

import com.brbx.domain.model.PlayerState
import com.brbx.domain.model.common.Track
import com.brbx.domain.model.enums.PlaybackStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

internal abstract class BasePlayerController(
    dispatcherMainImmediate: CoroutineDispatcher,
) : PlayerController {

    protected val scope = CoroutineScope(context = dispatcherMainImmediate + SupervisorJob())

    private val _state = MutableStateFlow(value = PlayerState())
    final override val playerState = _state.asStateFlow()

    protected var currentQueue: List<Track> = emptyList()
    protected var currentTrackIndex: Int = -1

    private var positionUpdateJob: Job? = null

    final override fun setQueue(tracks: List<Track>, startIndex: Int) {
        if (tracks.isEmpty()) {
            clearQueue()
        } else {
            initializeQueue(tracks = tracks, startIndex = startIndex)
        }
    }

    protected open fun clearQueue() {
        currentQueue = emptyList()
        currentTrackIndex = -1
        stopPositionUpdates()
        reduce { PlayerState() }
    }

    protected abstract fun initializeQueue(tracks: List<Track>, startIndex: Int)

    protected abstract fun getCurrentPositionMs(): Long

    protected fun reduce(transform: PlayerState.() -> PlayerState) =
        _state.update(function = transform)

    protected fun updatePosition(positionMs: Long) = reduce {
        copy(currentPositionMs = positionMs.coerceAt0())
    }

    protected fun handlePlayStateChange(isPlaying: Boolean, status: PlaybackStatus) {
        reduce {
            copy(
                status = status,
                currentPositionMs = getCurrentPositionMs().coerceAt0(),
                currentTrack = currentQueue.getOrNull(index = currentTrackIndex) ?: currentTrack,
            )
        }
        if (isPlaying) startPositionUpdates() else stopPositionUpdates()
    }

    protected fun startPositionUpdates() {
        stopPositionUpdates()
        positionUpdateJob = scope.launch {
            while (isActive) {
                updatePosition(positionMs = getCurrentPositionMs())
                delay(timeMillis = POSITION_UPDATE_INTERVAL_MS)
            }
        }
    }

    protected fun stopPositionUpdates() {
        positionUpdateJob?.cancel()
        positionUpdateJob = null
    }

    protected fun Long.coerceAt0(): Long = this.coerceAtLeast(minimumValue = 0L)

    companion object {
        protected const val POSITION_UPDATE_INTERVAL_MS = 250L
        protected const val SEEK_TO_PREVIOUS_THRESHOLD_MS = 5000L
    }
}
