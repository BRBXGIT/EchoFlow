package com.brbx.onboarding.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.brbx.design_system.components.components.EchoFlowIconButton
import com.brbx.design_system.theme.mDimens
import com.brbx.design_system.theme.mShapes
import com.brbx.onboarding.utils.scrollToNext
import com.brbx.onboarding.utils.scrollToPrevious
import dev.chiksmedina.solar.OutlineSolar
import dev.chiksmedina.solar.outline.Arrows
import dev.chiksmedina.solar.outline.arrows.ArrowLeft
import dev.chiksmedina.solar.outline.arrows.ArrowRight
import kotlinx.coroutines.launch

@Composable
internal fun OnboardingNavBar(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) =
    BottomAppBar(
        modifier = modifier
            .clip(
                shape = RoundedCornerShape(
                    topEnd = mShapes.extraLarge.topEnd,
                    topStart = mShapes.extraLarge.topStart,
                    bottomEnd = mShapes.zero.bottomEnd,
                    bottomStart = mShapes.zero.bottomStart,
                )
            )
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(mDimens.macro4),
            ) {
                val animationScope = rememberCoroutineScope()
                EchoFlowIconButton(
                    imageVector = OutlineSolar.Arrows.ArrowLeft,
                    enabled = pagerState.currentPage > 0,
                ) {
                    animationScope.launch { pagerState.scrollToPrevious() }
                }
                EchoFlowIconButton(
                    imageVector = OutlineSolar.Arrows.ArrowRight,
                    enabled = pagerState.currentPage < pagerState.pageCount - 1,
                ) {
                    animationScope.launch { pagerState.scrollToNext() }
                }
            }
        }
    }