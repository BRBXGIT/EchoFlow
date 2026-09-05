package com.brbx.design_system.components.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.brbx.debug.compose.EchoFlowPreview
import com.brbx.design_system.theme.mColors
import com.brbx.design_system.theme.mDimens
import com.brbx.design_system.theme.mShapes
import com.brbx.design_system.theme.mTypography

@Composable
fun MediaCard(
    title: String,
    description: String?,
    poster: String?,
    modifier: Modifier = Modifier,
) =
    CardBase(
        modifier = modifier,
        title = title,
        description = description,
        poster = poster,
    )

@Composable
fun UserCard(
    name: String,
    followersDescription: String,
    poster: String?,
    modifier: Modifier = Modifier,
) =
    CardBase(
        modifier = modifier,
        title = name,
        description = followersDescription,
        poster = poster,
        posterShape = CircleShape,
        alignment = Alignment.CenterHorizontally,
        innerColumnAlignment = Alignment.CenterHorizontally,
    )

@Composable
private fun CardBase(
    title: String,
    description: String?,
    poster: String?,
    modifier: Modifier = Modifier,
    posterShape: Shape = mShapes.medium,
    alignment: Alignment.Horizontal = Alignment.Start,
    innerColumnAlignment: Alignment.Horizontal = Alignment.Start,
) =
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(mDimens.micro4),
        horizontalAlignment = alignment,
    ) {
        EchoFlowRemoteImage(
            model = poster,
            modifier = Modifier
                .size(120.dp)
                .clip(posterShape)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(mDimens.zero),
            horizontalAlignment = innerColumnAlignment,
            modifier = Modifier.width(120.dp)
        ) {
            CardText(
                text = title,
                style = mTypography.bodyMedium.copy(
                    fontWeight = FontWeight.W600,
                    color = mColors.onBackground,
                )
            )

            description?.let {
                CardText(
                    text = description,
                    style = mTypography.labelMedium.copy(
                        color = mColors.onBackground.copy(alpha = 0.7f)
                    )
                )
            }
        }
    }

@Composable
private fun CardText(
    text: String,
    style: TextStyle,
) =
    Text(
        text = text,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = style,
    )

@Composable
@EchoFlowPreview
private fun MediaCardPreview() =
    MediaCard(
        title = "143 ways to lose yourself",
        description = "usedcvnt",
        poster = null,
    )

@Composable
@EchoFlowPreview
private fun UserCardPreview() =
    UserCard(
        name = "usedcvnt",
        followersDescription = "124K followers",
        poster = null,
    )