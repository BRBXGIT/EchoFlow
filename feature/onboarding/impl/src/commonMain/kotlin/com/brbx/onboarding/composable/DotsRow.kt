package com.brbx.onboarding.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.brbx.debug.compose.EchoFlowPreview
import com.brbx.design_system.theme.mColors
import com.brbx.design_system.theme.mDimens
import com.brbx.design_system.theme.mMotion

@Composable
internal fun DotsRow(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
) =
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(mDimens.micro3),
    ) {
        repeat(times = pageCount) { current ->
            val isSelected = current == currentPage
            val animatedBg by animateColorAsState(
                targetValue = if (isSelected) mColors.primary else mColors.surfaceContainerHigh,
                animationSpec = mMotion.nonSpatialFastSpec(),
            )

            Box(
                modifier = Modifier
                    .size(mDimens.micro4)
                    .background(color = animatedBg, shape = CircleShape)
            )
        }
    }

@EchoFlowPreview
@Composable
private fun DotsRowPreview() =
    DotsRow(pageCount = 3, currentPage = 2)