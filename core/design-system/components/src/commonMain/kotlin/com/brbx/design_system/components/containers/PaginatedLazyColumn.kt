package com.brbx.design_system.components.containers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.brbx.design_system.theme.mDimens
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

private const val LoadingPlaceholderKey = "PaginatedLazyColumn_DefaultLoadingPlaceholderKey"

@Composable
fun PaginatedLazyColumn(
    isLoading: Boolean,
    onLoadNext: () -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    contentPadding: PaddingValues = PaddingValues(all = mDimens.zero),
    loadBuffer: Int = 3,
    loadingPlaceholder: LazyListScope.() -> Unit = { defaultLoadingPlaceholder() },
    content: LazyListScope.() -> Unit,
) {
    LaunchedEffect(key1 = state) {
        snapshotFlow {
            val layoutInfo = state.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

            totalItems > 0 && lastVisibleItem >= totalItems - loadBuffer - 1
        }
            .distinctUntilChanged()
            .filter { shouldLoad -> shouldLoad }
            .collect {
                onLoadNext()
            }
    }

    LazyColumn(
        state = state,
        verticalArrangement = verticalArrangement,
        contentPadding = contentPadding,
        modifier = modifier,
    ) {
        content()

        if (isLoading) {
            loadingPlaceholder()
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
private fun LazyListScope.defaultLoadingPlaceholder() =
    item(key = LoadingPlaceholderKey) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .animateItem(),
            contentAlignment = Alignment.Center,
        ) {
            ContainedLoadingIndicator(modifier = Modifier.padding(all = mDimens.micro4))
        }
    }