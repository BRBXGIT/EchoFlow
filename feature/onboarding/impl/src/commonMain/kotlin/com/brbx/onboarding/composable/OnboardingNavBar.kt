package com.brbx.onboarding.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.brbx.debug.compose.EchoFlowPreview
import com.brbx.design_system.components.components.EchoFlowIconButton
import com.brbx.design_system.theme.mDimens
import com.brbx.design_system.theme.mShapes
import com.brbx.onboarding.view_model.OnboardingEffect
import dev.chiksmedina.solar.OutlineSolar
import dev.chiksmedina.solar.outline.Arrows
import dev.chiksmedina.solar.outline.arrows.ArrowLeft
import dev.chiksmedina.solar.outline.arrows.ArrowRight

@Composable
internal fun OnboardingNavBar(
    postScreenEffect: (OnboardingEffect) -> Unit,
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
                EchoFlowIconButton(imageVector = OutlineSolar.Arrows.ArrowLeft) {
                    postScreenEffect(OnboardingEffect.PreviousPage)
                }
                EchoFlowIconButton(imageVector = OutlineSolar.Arrows.ArrowRight) {
                    postScreenEffect(OnboardingEffect.NextPage)
                }
            }
        }
    }

@EchoFlowPreview
@Composable
private fun OnboardingNavBarPreview() =
    OnboardingNavBar(postScreenEffect = {})