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
import kotlin.time.Duration.Companion.milliseconds

internal abstract class BasePlayerController(
    dispatcherMainImmediate: CoroutineDispatcher,
) : PlayerController {

    protected val scope = CoroutineScope(dispatcherMainImmediate + SupervisorJob())

    private val _state = MutableStateFlow(PlayerState())
    final override val playerState = _state.asStateFlow()

    protected var currentQueue: List<Track> = emptyList()
    protected var currentTrackIndex: Int = -1

    private var positionUpdateJob: Job? = null

    // --- Queue Management ---

    final override fun setQueue(tracks: List<Track>, startIndex: Int) =
        if (tracks.isEmpty()) clearQueue() else initializeQueue(tracks, startIndex)

    final override fun addToQueue(track: Track?) {
        val t = track ?: return
        if (currentQueue.isEmpty()) return setQueue(listOf(t), 0)

        val insertIndex = currentQueue.size
        currentQueue = currentQueue + t
        reduce { copy(queue = currentQueue) }

        onTrackAdded(t, insertIndex)
    }

    protected open fun clearQueue() {
        currentQueue = emptyList()
        currentTrackIndex = -1
        stopPositionUpdates()
        reduce { PlayerState() }
    }

    protected abstract fun initializeQueue(tracks: List<Track>, startIndex: Int)

    protected abstract fun onTrackAdded(track: Track, insertIndex: Int)

    // --- State & Position Updates ---

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
                currentTrack = if (currentQueue.isEmpty()) null else currentQueue.getOrNull(currentTrackIndex) ?: currentTrack,
            )
        }
        if (isPlaying) startPositionUpdates() else stopPositionUpdates()
    }

    protected fun startPositionUpdates() {
        stopPositionUpdates()
        positionUpdateJob = scope.launch {
            while (isActive) {
                updatePosition(positionMs = getCurrentPositionMs())
                delay(duration = POSITION_UPDATE_INTERVAL_MS.milliseconds)
            }
        }
    }

    protected fun stopPositionUpdates() {
        positionUpdateJob?.cancel()
        positionUpdateJob = null
    }

    // --- Helpers & Constants ---

    protected fun Long.coerceAt0(): Long = coerceAtLeast(0L)

    companion object {
        protected const val POSITION_UPDATE_INTERVAL_MS = 250L
        protected const val SEEK_TO_PREVIOUS_THRESHOLD_MS = 5000L
    }
}
