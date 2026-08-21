package com.brbx.onboarding.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.brbx.debug.compose.EchoFlowPreview
import com.brbx.design_system.components.utils.safeStringResource
import com.brbx.design_system.theme.mColors
import com.brbx.design_system.theme.mDimens
import com.brbx.design_system.theme.mShapes
import com.brbx.design_system.theme.mTypography
import com.brbx.onboarding.view_model.onboarding_page.CommonPage
import com.brbx.onboarding.view_model.onboarding_page.OnboardingAction
import com.brbx.onboarding.view_model.onboarding_page.OnboardingPage
import com.brbx.onboarding.view_model.onboarding_page.StandardOnboardingPage
import echoflow.feature.onboarding.impl.generated.resources.Res
import echoflow.feature.onboarding.impl.generated.resources.label_skip_button
import org.jetbrains.compose.resources.StringResource

@Composable
internal fun StandardPageRenderer(
    page: StandardOnboardingPage,
    onSkip: () -> Unit,
    onAction: () -> Unit,
    modifier: Modifier = Modifier,
) =
    page.action?.let {
        WithAction(
            page = page,
            onSkip = onSkip,
            onAction = onAction,
            modifier = modifier,
        )
    } ?: WithoutAction(page)

@Composable
private fun WithoutAction(
    page: StandardOnboardingPage,
) =
    SpacedColumn {
        Title(res = page.title)
        IconsCollage(collage = page.collage)
        Description(res = page.description)
    }

@Composable
private fun WithAction(
    page: StandardOnboardingPage,
    onSkip: () -> Unit,
    onAction: () -> Unit,
    modifier: Modifier = Modifier,
) =
    Box(
        modifier = modifier,
    ) {
        SpacedColumn(
            modifier = Modifier.align(Alignment.TopCenter),
        ) {
            Title(res = page.title)
            Description(res = page.description)
        }
        IconsCollage(
            collage = page.collage,
            modifier = Modifier.align(Alignment.Center),
        )
        SpacedColumn(
            modifier = Modifier.align(Alignment.BottomCenter),
        ) {
            val macro2Height = mDimens.macro7
            val modifier = remember(key1 = macro2Height) {
                Modifier
                    .fillMaxWidth()
                    .height(macro2Height)
            }
            page.action?.let { action ->
                if (action.canSkip) {
                    SkipButton(
                        textRes = Res.string.label_skip_button,
                        onClick = onSkip,
                        modifier = modifier,
                    )
                }
                ActionButton(
                    textRes = action.text,
                    onClick = onAction,
                    modifier = modifier,
                )
            }
        }
    }

@Composable
private fun SkipButton(
    textRes: StringResource,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) =
    TextButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Text(
            text = safeStringResource(textRes),
            style = mTypography.bodyMedium,
            fontWeight = FontWeight.W600,
        )
    }

@Composable
private fun ActionButton(
    textRes: StringResource,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) =
    Button(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(
            text = safeStringResource(textRes),
            style = mTypography.bodyMedium,
        )
    }

@Composable
private fun Title(
    res: StringResource,
    modifier: Modifier = Modifier,
) =
    TextWrapper(
        res = res,
        style = mTypography.headlineMedium,
        modifier = modifier,
    )

@Composable
private fun Description(
    res: StringResource,
    modifier: Modifier = Modifier,
) =
    TextWrapper(
        res = res,
        style = mTypography.bodyLarge,
        modifier = modifier,
    )

@Composable
private fun TextWrapper(
    res: StringResource,
    style: TextStyle,
    modifier: Modifier = Modifier,
) =
    Text(
        text = safeStringResource(res),
        modifier = modifier,
        style = style,
        textAlign = TextAlign.Center,
        color = mColors.onBackground,
    )

@Composable
private fun IconsCollage(
    collage: StandardOnboardingPage.IconCollage,
    modifier: Modifier = Modifier
) =
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(all = mDimens.macro8)
    ) {
        CollageIconWrapper(
            icon = collage.topStart,
            shape = mShapes.medium,
            containerColor = mColors.secondaryContainer,
            contentColor = mColors.onSecondaryContainer,
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = mDimens.micro8, y = mDimens.macro4)
                .graphicsLayer { rotationZ = -12f }
                .size(64.dp)
        )

        CollageIconWrapper(
            icon = collage.topEnd,
            shape = mShapes.large,
            containerColor = mColors.tertiaryContainer,
            contentColor = mColors.onTertiaryContainer,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = -mDimens.micro8, y = mDimens.micro8)
                .graphicsLayer { rotationZ = 15f }
                .size(72.dp)
        )

        CollageIconWrapper(
            icon = collage.bottomStart,
            shape = mShapes.small,
            containerColor = mColors.surfaceVariant,
            contentColor = mColors.onSurfaceVariant,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = mDimens.macro2, y = -mDimens.micro8)
                .graphicsLayer { rotationZ = -10f }
                .size(56.dp)
        )

        CollageIconWrapper(
            icon = collage.bottomEnd,
            shape = CircleShape,
            containerColor = mColors.primary,
            contentColor = mColors.onPrimary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = -mDimens.macro4, y = -mDimens.macro2)
                .graphicsLayer { rotationZ = 8f }
                .size(64.dp)
        )

        CollageIconWrapper(
            icon = collage.center,
            shape = mShapes.extraLarge,
            containerColor = mColors.primaryContainer,
            contentColor = mColors.onPrimaryContainer,
            modifier = Modifier
                .align(Alignment.Center)
                .size(120.dp),
            iconSize = 56.dp,
        )
    }

@Composable
private fun CollageIconWrapper(
    icon: ImageVector,
    shape: Shape,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    iconSize: Dp = 32.dp
) =
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.background(color = containerColor, shape = shape)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(iconSize)
        )
    }

@EchoFlowPreview
@Composable
private fun OnboardingPageWithoutActionPreview() =
    PreviewInternal(page = CommonPage.Greeting)

@EchoFlowPreview
@Composable
private fun OnboardingPageWithActionPreview() =
    PreviewInternal(page = CommonPage.Authentication)

@EchoFlowPreview
@Composable
private fun OnboardingPageWithActionAndSkipPreview() =
    PreviewInternal(
        page = StandardOnboardingPage(
            title = CommonPage.Authentication.title,
            description = CommonPage.Authentication.description,
            collage = CommonPage.Authentication.collage,
            action = (CommonPage.Authentication.action as? OnboardingAction.Authenticate)?.copy(canSkip = true)
        )
    )

@Composable
private fun PreviewInternal(page: OnboardingPage) =
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        OnboardingRendererRegistryImpl().Render(
            onSkip = {},
            onAction = {},
            page = page,
            modifier = Modifier
                .fillMaxSize()
                .padding(all = mDimens.micro8),
        )
    }