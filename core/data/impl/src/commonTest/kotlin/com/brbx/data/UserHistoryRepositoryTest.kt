package com.brbx.data

import com.brbx.data.handler.NetworkResponseHandlerImpl
import com.brbx.data.repository.UserHistoryRepositoryImpl
import com.brbx.domain.model.base.ItemsCollection
import com.brbx.domain.model.common.RequestResult
import com.brbx.domain.model.common.Track
import com.brbx.domain.model.enums.RequestException
import com.brbx.network.api.UserFeedApi
import com.brbx.network.model.base.CollectionDto
import com.brbx.network.model.common.TrackDto
import kotlinx.coroutines.test.runTest
import kotlinx.io.IOException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserHistoryRepositoryTest {

    private val fakeUserFeedApi = FakeUserFeedApi()
    private val handler = NetworkResponseHandlerImpl()
    private val repository = UserHistoryRepositoryImpl(
        userFeedApi = fakeUserFeedApi,
        handler = handler,
    )

    @Test
    fun `recentTracks flow has empty collection initially`() = runTest {
        val initialCollection = repository.recentTracks.value

        assertEquals(expected = ItemsCollection(), actual = initialCollection)
    }

    @Test
    fun `loadRecentTracks updates recentTracks flow on success`() = runTest {
        fakeUserFeedApi.responseToReturn = CollectionDto(
            collection = listOf(
                TrackDto(id = 101L, title = "History Track 1"),
                TrackDto(id = 102L, title = "History Track 2"),
            )
        )

        val result = repository.loadRecentTracks()

        assertTrue(actual = result is RequestResult.Success)
        val resultCollection = result.value
        assertEquals(expected = 2, actual = resultCollection.collection.size)
        assertEquals(expected = "History Track 1", actual = resultCollection.collection[0].title)

        val cachedCollection = repository.recentTracks.value
        assertEquals(expected = resultCollection, actual = cachedCollection)
    }

    @Test
    fun `loadRecentTracks returns exception when network fails`() = runTest {
        fakeUserFeedApi.shouldThrowIOException = true

        val result = repository.loadRecentTracks()

        assertTrue(actual = result is RequestResult.Exception)
        assertEquals(expected = RequestException.Internet, actual = result.value)
    }

    private class FakeUserFeedApi : UserFeedApi {
        var responseToReturn: CollectionDto<TrackDto> = CollectionDto(collection = emptyList())
        var shouldThrowIOException: Boolean = false

        override suspend fun getRecentlyPlayedTracks(): CollectionDto<TrackDto> {
            if (shouldThrowIOException) throw IOException("Network error")
            return responseToReturn
        }
    }
}
