package com.brbx.domain.model.paging

import androidx.paging.PagingData
import com.brbx.domain.model.Track
import kotlinx.coroutines.flow.Flow

typealias TrackFlow = Flow<PagingData<Track>>
