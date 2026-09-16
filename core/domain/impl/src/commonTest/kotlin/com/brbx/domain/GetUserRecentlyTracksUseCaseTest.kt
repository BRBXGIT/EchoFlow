package com.brbx.domain

import com.brbx.data.repository.UserHistoryRepository
import com.brbx.domain.model.base.ItemsCollection
import com.brbx.domain.model.common.RequestResult
import com.brbx.domain.model.common.Track
import com.brbx.domain.model.common.failure
import com.brbx.domain.model.common.success
import com.brbx.domain.model.enums.RequestException
import com.brbx.domain.use_case.GetUserRecentlyTracksUseCaseImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetUserRecentlyTracksUseCaseTest {

    private val fakeUserHistoryRepository = FakeUserHistoryRepository()
    private val useCase =
        GetUserRecentlyTracksUseCaseImpl(historyRepository = fakeUserHistoryRepository)

    @Test
    fun `invoke returns success with tracks when historyRepository loads successfully`() = runTest {
        val tracksList = listOf(
            Track(id = 1L, title = "Track 1", description = null, artworkUrl = null, user = null),
            Track(id = 2L, title = "Track 2", description = null, artworkUrl = null, user = null),
        )
        fakeUserHistoryRepository.resultToReturn = success(value = ItemsCollection(collection = tracksList))

        val result = useCase()

        assertTrue(actual = result is RequestResult.Success)
        assertEquals(expected = tracksList, actual = result.value.collection)
    }

    @Test
    fun `invoke returns failure when historyRepository fails`() = runTest {
        fakeUserHistoryRepository.resultToReturn = failure(exception = RequestException.Internet)

        val result = useCase()

        assertTrue(actual = result is RequestResult.Exception)
        assertEquals(expected = RequestException.Internet, actual = result.value)
    }

    private class FakeUserHistoryRepository : UserHistoryRepository {
        override val recentTracks = MutableStateFlow<ItemsCollection<Track>?>(value = ItemsCollection())
        var resultToReturn: RequestResult<ItemsCollection<Track>> = success(value = ItemsCollection())

        override suspend fun loadRecentTracks(): RequestResult<ItemsCollection<Track>> = resultToReturn
    }
}
