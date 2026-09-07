package com.brbx.home.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.brbx.home.view_model.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun HomeScaffold() {
    val viewModel = koinViewModel<HomeViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val relatedToRecent = state.feed.relatedToRecent?.collectAsLazyPagingItems()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        HomeContent(
            relatedToRecent = relatedToRecent,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
        )
    }
}