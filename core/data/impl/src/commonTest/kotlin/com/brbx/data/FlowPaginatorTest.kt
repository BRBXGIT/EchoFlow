package com.brbx.data

import com.brbx.data.pagination.FlowPaginator
import com.brbx.domain.model.common.failure
import com.brbx.domain.model.common.success
import com.brbx.domain.model.enums.RequestException
import com.brbx.network.model.base.PaginatedDto
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class FlowPaginatorTest {

    @Test
    fun `initial state is empty and not loading`() = runTest {
        val paginator = FlowPaginator(
            call = { success(value = PaginatedDto<String>(collection = emptyList())) },
            mapper = { dto -> dto }
        )

        val initialState = paginator.state.value

        assertEquals(expected = emptyList(), actual = initialState.items)
        assertFalse(actual = initialState.isLoading)
        assertFalse(actual = initialState.isRefreshing)
        assertFalse(actual = initialState.endReached)
        assertNull(actual = initialState.exception)
    }

    @Test
    fun `loadNext loads first page with null key and appends mapped items`() = runTest {
        var requestedKey: String? = "INITIAL_NON_NULL"
        val paginator = FlowPaginator(
            call = { key ->
                requestedKey = key
                success(
                    value = PaginatedDto(
                        collection = listOf(1, 2),
                        nextHref = "next_page_token"
                    )
                )
            },
            mapper = { dto -> "Item_$dto" }
        )

        paginator.loadNext()

        assertEquals(expected = null, actual = requestedKey)
        val state = paginator.state.first()
        assertEquals(expected = listOf("Item_1", "Item_2"), actual = state.items)
        assertFalse(actual = state.isLoading)
        assertFalse(actual = state.endReached)
        assertNull(actual = state.exception)
    }

    @Test
    fun `loadNext passes nextHref key on second page load and appends items`() = runTest {
        val requestedKeys = mutableListOf<String?>()
        val paginator = FlowPaginator(
            call = { key ->
                requestedKeys.add(element = key)
                if (key == null) {
                    success(
                        value = PaginatedDto(
                            collection = listOf(1, 2),
                            nextHref = "page_2_href"
                        )
                    )
                } else {
                    success(
                        value = PaginatedDto(
                            collection = listOf(3),
                            nextHref = null
                        )
                    )
                }
            },
            mapper = { dto -> "Item_$dto" }
        )

        paginator.loadNext()
        paginator.loadNext()

        assertEquals(expected = listOf(null, "page_2_href"), actual = requestedKeys)
        val state = paginator.state.first()
        assertEquals(expected = listOf("Item_1", "Item_2", "Item_3"), actual = state.items)
        assertTrue(actual = state.endReached)
        assertFalse(actual = state.isLoading)
    }

    @Test
    fun `loadNext does not invoke call when endReached is true`() = runTest {
        var callCount = 0
        val paginator = FlowPaginator(
            call = {
                callCount++
                success(
                    value = PaginatedDto(
                        collection = listOf(1),
                        nextHref = null
                    )
                )
            },
            mapper = { dto -> "Item_$dto" }
        )

        paginator.loadNext()
        assertEquals(expected = 1, actual = callCount)
        assertTrue(actual = paginator.state.value.endReached)

        paginator.loadNext()
        assertEquals(expected = 1, actual = callCount)
    }

    @Test
    fun `refresh resets key to null and replaces items`() = runTest {
        var requestedKey: String? = "SOME_KEY"
        var callCount = 0
        val paginator = FlowPaginator(
            call = { key ->
                callCount++
                requestedKey = key
                if (callCount == 1) {
                    success(
                        value = PaginatedDto(
                            collection = listOf(1, 2),
                            nextHref = "page_2_href"
                        )
                    )
                } else {
                    success(
                        value = PaginatedDto(
                            collection = listOf(99),
                            nextHref = null
                        )
                    )
                }
            },
            mapper = { dto -> "Item_$dto" }
        )

        paginator.loadNext()
        assertEquals(expected = listOf("Item_1", "Item_2"), actual = paginator.state.value.items)

        paginator.refresh()

        assertEquals(expected = null, actual = requestedKey)
        val state = paginator.state.value
        assertEquals(expected = listOf("Item_99"), actual = state.items)
        assertTrue(actual = state.endReached)
        assertFalse(actual = state.isRefreshing)
    }

    @Test
    fun `loadNext sets exception on network failure`() = runTest {
        val paginator = FlowPaginator<Int, String>(
            call = { failure(exception = RequestException.Internet) },
            mapper = { dto -> "Item_$dto" }
        )

        paginator.loadNext()

        val state = paginator.state.value
        assertEquals(expected = RequestException.Internet, actual = state.exception)
        assertFalse(actual = state.isLoading)
        assertEquals(expected = emptyList(), actual = state.items)
    }
}
