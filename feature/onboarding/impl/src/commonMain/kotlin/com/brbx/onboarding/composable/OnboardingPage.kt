package com.brbx.onboarding.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
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
import androidx.window.core.layout.WindowSizeClass
import com.brbx.debug.compose.EchoFlowPreview
import com.brbx.design_system.theme.mColors
import com.brbx.design_system.theme.mDimens
import com.brbx.design_system.theme.mShapes
import com.brbx.design_system.theme.mTypography
import com.brbx.onboarding.model.OnboardingPage
import com.brbx.onboarding.model.createAuthPage
import com.brbx.onboarding.model.createGreetingPage
import echoflow.feature.onboarding.impl.generated.resources.Res
import echoflow.feature.onboarding.impl.generated.resources.label_skip_button
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun OnboardingPage(
    page: OnboardingPage<*>,
    onSkip: () -> Unit,
    onAction: () -> Unit,
    modifier: Modifier = Modifier,
) =
    if (rememberIsLargeScreen()) {
        LargeLayout(page, onSkip, onAction = onAction, modifier = modifier.fillMaxSize())
    } else {
        StandardLayout(page, onSkip, onAction = onAction, modifier = modifier.fillMaxSize())
    }

@Composable
private fun rememberIsLargeScreen() =
    currentWindowAdaptiveInfoV2().let { adaptiveInfo ->
        remember(key1 = adaptiveInfo) {
            adaptiveInfo
                .windowSizeClass
                .isWidthAtLeastBreakpoint(widthDpBreakpoint = WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)
        }
    }

@Composable
private fun StandardLayout(
    page: OnboardingPage<*>,
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
    } ?: WithoutAction(page, modifier)

@Composable
private fun LargeLayout(
    page: OnboardingPage<*>,
    onSkip: () -> Unit,
    onAction: () -> Unit,
    modifier: Modifier = Modifier,
) =
    Row(
        modifier = modifier.padding(mDimens.macro8),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(mDimens.macro8)
    ) {
        IconsCollage(
            collage = page.collage,
            modifier = Modifier.weight(1f)
        )
        SpacedColumn(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(mDimens.micro6, Alignment.CenterVertically)
        ) {
            Title(res = page.title, textAlign = TextAlign.Start)
            Description(res = page.description, textAlign = TextAlign.Start)

            page.action?.let { action ->
                Spacer(modifier = Modifier.height(mDimens.macro2))
                OnboardingButtons(
                    action = action,
                    onSkip = onSkip,
                    onAction = onAction,
                    horizontalAlignment = Alignment.Start
                )
            }
        }
    }

@Composable
private fun WithoutAction(
    page: OnboardingPage<*>,
    modifier: Modifier = Modifier,
) =
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        SpacedColumn {
            Title(res = page.title)
            IconsCollage(collage = page.collage)
            Description(res = page.description)
        }
    }

@Composable
private fun WithAction(
    page: OnboardingPage<*>,
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
        page.action?.let { action ->
            OnboardingButtons(
                action = action,
                onSkip = onSkip,
                onAction = onAction,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }

@Composable
private fun OnboardingButtons(
    action: OnboardingPage.Action,
    onSkip: () -> Unit,
    onAction: () -> Unit,
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
) =
    SpacedColumn(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment,
    ) {
        val macro8Height = mDimens.macro8
        val buttonModifier = remember(key1 = macro8Height) {
            Modifier
                .fillMaxWidth()
                .height(macro8Height)
        }
        if (action.canSkip) {
            SkipButton(
                textRes = Res.string.label_skip_button,
                onClick = onSkip,
                modifier = buttonModifier,
            )
        }
        ActionButton(
            action = action,
            onClick = onAction,
            modifier = buttonModifier,
        )
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
            text = stringResource(textRes),
            style = mTypography.bodyLarge,
            fontWeight = FontWeight.W600,
        )
    }

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun ActionButton(
    action: OnboardingPage.Action,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) =
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = action.enabled
    ) {
        if (action.isLoading) {
            ContainedLoadingIndicator(modifier = Modifier.height(mDimens.micro6))
        } else {
            val textRes = rememberActionButtonText(action.enabled, action.disabledText, action.enabledText)
            Text(
                text = stringResource(textRes),
                style = mTypography.bodyLarge,
            )
        }
    }

@Composable
private fun rememberActionButtonText(
    enabled: Boolean,
    disabledText: StringResource?,
    enabledText: StringResource
) =
    remember(key1 = enabled, key2 = disabledText, key3 = enabledText) {
        if (!enabled && disabledText != null) {
            disabledText
        } else {
            enabledText
        }
    }

@Composable
private fun Title(
    res: StringResource,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
) =
    TextWrapper(
        res = res,
        style = mTypography.headlineMedium,
        modifier = modifier,
        textAlign = textAlign,
    )

@Composable
private fun Description(
    res: StringResource,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
) =
    TextWrapper(
        res = res,
        style = mTypography.bodyLarge,
        modifier = modifier,
        textAlign = textAlign,
    )

@Composable
private fun TextWrapper(
    res: StringResource,
    style: TextStyle,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
) =
    Text(
        text = stringResource(res),
        modifier = modifier,
        style = style,
        textAlign = textAlign,
        color = mColors.onBackground,
    )

@Composable
private fun IconsCollage(
    collage: OnboardingPage.IconCollage,
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


@Composable
@EchoFlowPreview
private fun WithSkipPagePreview() =
    OnboardingPage(
        page = createAuthPage(payload = 0).copy(action = createAuthPage(payload = 0).action?.copy(canSkip = true)),
        onSkip = {},
        onAction = {},
        modifier = Modifier.fillMaxSize().padding(all = mDimens.micro8)
    )

@Composable
@EchoFlowPreview
private fun WithActionPagePreview() =
    OnboardingPage(
        page = createAuthPage(payload = 0),
        onSkip = {},
        onAction = {},
        modifier = Modifier.fillMaxSize().padding(all = mDimens.micro8)
    )

@Composable
@EchoFlowPreview
private fun NoActionPagePreview() =
    OnboardingPage(
        page = createGreetingPage(payload = 0),
        onSkip = {},
        onAction = {},
        modifier = Modifier.fillMaxSize().padding(all = mDimens.micro8)
    )
