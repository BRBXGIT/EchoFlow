package com.brbx.onboarding.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.brbx.onboarding.view_model.model.OnboardingPage
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.brbx.design_system.theme.mColors
import com.brbx.design_system.theme.mShapes

@Composable
internal fun OnboardingPage(
    page: OnboardingPage,
    modifier: Modifier = Modifier,
) {

}

@Composable
private fun WithoutAction(
    page: OnboardingPage,
    modifier: Modifier = Modifier,
) {

}

@Composable
private fun IconsCollage(
    collage: OnboardingPage.IconCollage,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(all = 16.dp)
    ) {
        // Левая верхняя (Средний размер, среднее скругление, небольшой наклон влево)
        CollageIconWrapper(
            icon = collage.topStart,
            shape = mShapes.medium,
            containerColor = mColors.secondaryContainer,
            contentColor = mColors.onSecondaryContainer,
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 16.dp, y = 32.dp)
                .rotate(-12f)
                .size(64.dp)
        )

        // Правая верхняя (Большой размер, крупное скругление, наклон вправо)
        CollageIconWrapper(
            icon = collage.topEnd,
            shape = mShapes.large,
            containerColor = mColors.tertiaryContainer,
            contentColor = mColors.onTertiaryContainer,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = -16.dp, y = 16.dp)
                .rotate(15f)
                .size(72.dp)
        )

        // Левая нижняя (Маленькая, минимальное скругление)
        CollageIconWrapper(
            icon = collage.bottomStart,
            shape = mShapes.small,
            containerColor = mColors.surfaceVariant,
            contentColor = mColors.onSurfaceVariant,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = 24.dp, y = -16.dp)
                .rotate(-10f)
                .size(56.dp)
        )

        // Правая нижняя (Идеальный круг, контрастный primary цвет)
        CollageIconWrapper(
            icon = collage.bottomEnd,
            shape = CircleShape, // Добавим CircleShape для разнообразия геометрических форм
            containerColor = mColors.primary,
            contentColor = mColors.onPrimary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = -32.dp, y = -24.dp)
                .rotate(8f)
                .size(64.dp)
        )

        // Центральная (Самая крупная, по центру, без наклона, максимальное скругление)
        // Располагаем её последней в коде, чтобы она отрисовалась поверх остальных (z-index)
        CollageIconWrapper(
            icon = collage.center,
            shape = mShapes.extraLarge,
            containerColor = mColors.primaryContainer,
            contentColor = mColors.onPrimaryContainer,
            modifier = Modifier
                .align(Alignment.Center)
                .size(120.dp),
            iconSize = 56.dp // Сама иконка тоже крупнее
        )
    }
}

@Composable
private fun CollageIconWrapper(
    icon: ImageVector,
    shape: Shape,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    iconSize: Dp = 32.dp
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(shape)
            .background(containerColor)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(iconSize)
        )
    }
}