package com.brbx.onboarding.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import com.brbx.debug.compose.EchoFlowPreview
import com.brbx.design_system.theme.mMotion
import kotlin.math.absoluteValue

@Composable
internal fun OnboardingPager(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.(page: Int) -> Unit,
) =
    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        flingBehavior = PagerDefaults.flingBehavior(
            state = pagerState,
            snapAnimationSpec = mMotion.slowSpatialSpec(),
        )
    ) { page ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    val pageOffset = pagerState.calculatePageOffset(page)
                    val absoluteOffset = pageOffset.absoluteValue
                    translationX = pageOffset * size.width
                    alpha = if (absoluteOffset <= 0.5f) {
                        1f - (absoluteOffset * 2f)
                    } else 0f
                }
        ) {
            content(page)
        }
    }

private fun PagerState.calculatePageOffset(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}

@Composable
@EchoFlowPreview
private fun OnboardingPagerPreview() {
    val pagerState = rememberPagerState { 3 }
    OnboardingPager(pagerState = pagerState) { page ->
        Text("Page $page")
    }
}
