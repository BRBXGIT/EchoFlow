package com.brbx.data

import com.brbx.data.handler.NetworkResponseHandlerImpl
import com.brbx.data.repository.RelatedRepositoryImpl
import com.brbx.network.api.RelatedApi
import com.brbx.network.model.base.PaginatedDto
import com.brbx.network.model.common.TrackDto
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class RelatedRepositoryTest {

    private val fakeRelatedApi = FakeRelatedApi()
    private val handler = NetworkResponseHandlerImpl()
    private val repository = RelatedRepositoryImpl(
        relatedApi = fakeRelatedApi,
        handler = handler,
    )

    @Test
    fun `getSimilarTracksPaginator initializes and automatically loads first page`() = runTest {
        val targetTrackId = 555L
        fakeRelatedApi.responseToReturn = PaginatedDto(
            collection = listOf(
                TrackDto(id = 1L, title = "Similar Track 1"),
                TrackDto(id = 2L, title = "Similar Track 2"),
            ),
            nextHref = "https://api.example.com/tracks?page=2",
        )

        val paginator = repository.getSimilarTracksPaginator(id = targetTrackId)
        val state = paginator.state.value

        assertEquals(expected = targetTrackId, actual = fakeRelatedApi.lastRequestedId)
        assertEquals(expected = null, actual = fakeRelatedApi.lastRequestedUrl)
        assertEquals(expected = 2, actual = state.items.size)
        assertEquals(expected = "Similar Track 1", actual = state.items[0].title)
        assertEquals(expected = "Similar Track 2", actual = state.items[1].title)
        assertFalse(actual = state.endReached)
    }

    @Test
    fun `getSimilarTracksPaginator loads next page when requested`() = runTest {
        fakeRelatedApi.responseToReturn = PaginatedDto(
            collection = listOf(TrackDto(id = 10L, title = "Track 10")),
            nextHref = "next_page_token",
        )

        val paginator = repository.getSimilarTracksPaginator(id = 100L)

        fakeRelatedApi.responseToReturn = PaginatedDto(
            collection = listOf(TrackDto(id = 20L, title = "Track 20")),
            nextHref = null,
        )

        paginator.loadNext()

        val state = paginator.state.value
        assertEquals(expected = "next_page_token", actual = fakeRelatedApi.lastRequestedUrl)
        assertEquals(expected = 2, actual = state.items.size)
        assertEquals(expected = "Track 20", actual = state.items[1].title)
    }

    private class FakeRelatedApi : RelatedApi {
        var responseToReturn: PaginatedDto<TrackDto> = PaginatedDto(collection = emptyList())
        var lastRequestedId: Long? = null
        var lastRequestedUrl: String? = null

        override suspend fun getSimilarTracks(id: Long, url: String?): PaginatedDto<TrackDto> {
            lastRequestedId = id
            lastRequestedUrl = url
            return responseToReturn
        }
    }
}
