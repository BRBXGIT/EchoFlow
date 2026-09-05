package com.brbx.design_system.components.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageScope
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
fun EchoFlowRemoteImage(
    model: String?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    filterQuality: FilterQuality = FilterQuality.Low,
    contentScale: ContentScale = ContentScale.Crop,
    crossfadeDuration: Int = 300,
    onLoading: @Composable (SubcomposeAsyncImageScope.(AsyncImagePainter.State.Loading) -> Unit)? = null,
    onError: @Composable (SubcomposeAsyncImageScope.(AsyncImagePainter.State.Error) -> Unit)? = null,
) {
    val context = LocalPlatformContext.current

    val imageRequest = remember(key1 = model, key2 = crossfadeDuration, key3 = context) {
        ImageRequest.Builder(context)
            .data(model)
            .crossfade(enable = true)
            .crossfade(durationMillis = crossfadeDuration)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build()
    }

    SubcomposeAsyncImage(
        model = imageRequest,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
        filterQuality = filterQuality,
        loading = onLoading,
        error = onError,
    )
}