package com.brbx.domain

import com.brbx.data.repository.RelatedRepository
import com.brbx.data.repository.UserHistoryRepository
import com.brbx.domain.model.base.ItemsCollection
import com.brbx.domain.model.common.RequestResult
import com.brbx.domain.model.common.Track
import com.brbx.domain.model.common.success
import com.brbx.domain.pagination.PaginationState
import com.brbx.domain.pagination.Paginator
import com.brbx.domain.use_case.GetUserRelatedTracksUseCaseImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetUserRelatedTracksUseCaseTest {

    private val fakeUserHistoryRepository = FakeUserHistoryRepository()
    private val fakeRelatedRepository = FakeRelatedRepository()
    private val useCase = GetUserRelatedTracksUseCaseImpl(
        historyRepository = fakeUserHistoryRepository,
        relatedRepository = fakeRelatedRepository,
    )

    @Test
    fun `invoke retrieves first track from recentTracks and requests similar tracks paginator`() = runTest {
        val targetTrackId = 888L
        val recentTracksList = listOf(
            Track(id = targetTrackId, title = "First Recent Track", description = null, artworkUrl = null, user = null),
            Track(id = 999L, title = "Second Recent Track", description = null, artworkUrl = null, user = null),
        )
        fakeUserHistoryRepository.recentTracks.value = ItemsCollection(collection = recentTracksList)

        val paginator = useCase()

        assertEquals(expected = targetTrackId, actual = fakeRelatedRepository.lastRequestedId)
        assertEquals(expected = fakeRelatedRepository.fakePaginator, actual = paginator)
    }

    private class FakeUserHistoryRepository : UserHistoryRepository {
        override val recentTracks = MutableStateFlow<ItemsCollection<Track>?>(value = null)

        override suspend fun loadRecentTracks(): RequestResult<ItemsCollection<Track>> =
            success(value = ItemsCollection())
    }

    private class FakeRelatedRepository : RelatedRepository {
        var lastRequestedId: Long? = null
        val fakePaginator = FakePaginator()

        override suspend fun getSimilarTracksPaginator(id: Long): Paginator<Track> {
            lastRequestedId = id
            return fakePaginator
        }
    }

    private class FakePaginator : Paginator<Track> {
        override val state: StateFlow<PaginationState<Track>> = MutableStateFlow(value = PaginationState())

        override suspend fun loadNext() {}

        override suspend fun refresh() {}
    }
}
