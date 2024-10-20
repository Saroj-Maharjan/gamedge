/*
 * Copyright 2021 Paul Rybitskyi, paul.rybitskyi.work@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("LongMethod")

package com.paulrybitskyi.gamedge.feature.info.presentation.widgets.header

import android.content.Context
import android.content.res.ColorStateList
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.NoOpUpdate
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.paulrybitskyi.commons.ktx.getCompatDrawable
import com.paulrybitskyi.commons.ktx.onClick
import com.paulrybitskyi.gamedge.common.ui.clickable
import com.paulrybitskyi.gamedge.common.ui.theme.GamedgeTheme
import com.paulrybitskyi.gamedge.common.ui.theme.lightScrim
import com.paulrybitskyi.gamedge.common.ui.theme.subtitle3
import com.paulrybitskyi.gamedge.common.ui.widgets.GameCover
import com.paulrybitskyi.gamedge.common.ui.widgets.Info
import com.paulrybitskyi.gamedge.feature.info.R
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.header.artworks.Artworks
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.header.artworks.GameInfoArtworkUiModel
import com.paulrybitskyi.gamedge.core.R as CoreR

private const val ConstraintIdArtworks = "artworks"
private const val ConstraintIdArtworksScrim = "artworks_scrim"
private const val ConstraintIdBackButton = "back_button"
private const val ConstraintIdPageIndicator = "page_indicator"
private const val ConstraintIdBackdrop = "backdrop"
private const val ConstraintIdCoverSpace = "cover_space"
private const val ConstraintIdCover = "cover"
private const val ConstraintIdLikeButton = "like_button"
private const val ConstraintIdFirstTitle = "first_title"
private const val ConstraintIdSecondTitle = "second_title"
private const val ConstraintIdReleaseDate = "release_date"
private const val ConstraintIdDeveloperName = "developer_name"
private const val ConstraintIdRating = "rating"
private const val ConstraintIdLikeCount = "like_count"
private const val ConstraintIdAgeRating = "age_rating"
private const val ConstraintIdGameCategory = "game_category"

private val CoverSpace = 40.dp
private val InfoIconSize = 34.dp

@Composable
internal fun GameInfoHeader(
    headerInfo: GameInfoHeaderUiModel,
    onArtworkClicked: (artworkIndex: Int) -> Unit,
    onBackButtonClicked: () -> Unit,
    onCoverClicked: () -> Unit,
    onLikeButtonClicked: () -> Unit,
) {
    val colors = GamedgeTheme.colors
    val density = LocalDensity.current
    val artworks = headerInfo.artworks
    val isPageIndicatorVisible by remember(artworks) { mutableStateOf(artworks.size > 1) }
    var selectedArtworkPage by rememberSaveable { mutableIntStateOf(0) }
    var secondTitleText by rememberSaveable { mutableStateOf("") }
    val isSecondTitleVisible by remember {
        derivedStateOf {
            secondTitleText.isNotEmpty()
        }
    }

    ConstraintLayout(
        constraintSet = constructExpandedConstraintSet(),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Artworks(
            artworks = artworks,
            isScrollingEnabled = true,
            modifier = Modifier.layoutId(ConstraintIdArtworks),
            onArtworkChanged = { page ->
                selectedArtworkPage = page
            },
            onArtworkClicked = onArtworkClicked,
        )

        Box(
            modifier = Modifier
                .layoutId(ConstraintIdArtworksScrim)
                .background(Color.Transparent),
        )

        Icon(
            painter = painterResource(CoreR.drawable.arrow_left),
            contentDescription = null,
            modifier = Modifier
                .layoutId(ConstraintIdBackButton)
                .statusBarsPadding()
                .size(56.dp)
                .clickable(
                    indication = ripple(
                        bounded = false,
                        radius = 18.dp,
                    ),
                    onClick = onBackButtonClicked,
                )
                .padding(GamedgeTheme.spaces.spacing_2_5)
                .background(
                    color = GamedgeTheme.colors.lightScrim,
                    shape = CircleShape,
                )
                .padding(GamedgeTheme.spaces.spacing_1_5),
            tint = Color.White,
        )

        if (isPageIndicatorVisible) {
            Text(
                text = stringResource(
                    R.string.game_info_header_page_indicator_template,
                    selectedArtworkPage + 1,
                    headerInfo.artworks.size,
                ),
                modifier = Modifier
                    .layoutId(ConstraintIdPageIndicator)
                    .statusBarsPadding()
                    .background(
                        color = GamedgeTheme.colors.lightScrim,
                        shape = RoundedCornerShape(20.dp),
                    )
                    .padding(
                        vertical = GamedgeTheme.spaces.spacing_1_5,
                        horizontal = GamedgeTheme.spaces.spacing_2_0,
                    ),
                color = Color.White,
                style = GamedgeTheme.typography.subtitle3,
            )
        }

        Box(
            modifier = Modifier
                .layoutId(ConstraintIdBackdrop)
                .shadow(
                    elevation = GamedgeTheme.spaces.spacing_0_5,
                    shape = RectangleShape,
                    clip = false,
                )
                .background(
                    color = GamedgeTheme.colors.surface,
                    shape = RectangleShape,
                )
                .clip(RectangleShape),
        )

        Spacer(
            modifier = Modifier
                .layoutId(ConstraintIdCoverSpace)
                .height(CoverSpace),
        )

        GameCover(
            title = null,
            imageUrl = headerInfo.coverImageUrl,
            modifier = Modifier.layoutId(ConstraintIdCover),
            onCoverClicked = if (headerInfo.hasCoverImageUrl) onCoverClicked else null,
        )

        // Animated selector drawables are not currently supported by the Jetpack Compose:
        // https://issuetracker.google.com/issues/212418566. However, since the link/unlike
        // animation is so gorgeous, it'd sad if we didn't use it, so we are using the legacy
        // View here to render it. Consider to migrate to the Jetpack Compose when the support
        // arrives.
        AndroidView(
            factory = { context ->
                LikeButton(context).apply {
                    supportBackgroundTintList = ColorStateList.valueOf(colors.secondary.toArgb())
                    size = FloatingActionButton.SIZE_NORMAL
                    setMaxImageSize(with(density) { 52.dp.toPx().toInt() })
                    setImageDrawable(context.getCompatDrawable(CoreR.drawable.heart_animated_selector))
                    supportImageTintList = ColorStateList.valueOf(colors.onSecondary.toArgb())
                    onClick { onLikeButtonClicked() }
                }
            },
            modifier = Modifier.layoutId(ConstraintIdLikeButton),
            // Have to provide any lambda here for optimizations to kick in (even if it's a no-op)
            onReset = NoOpUpdate,
            update = { view ->
                view.isLiked = headerInfo.isLiked
            },
        )

        Text(
            text = headerInfo.title,
            modifier = Modifier.layoutId(ConstraintIdFirstTitle),
            color = GamedgeTheme.colors.onPrimary,
            maxLines = 1,
            onTextLayout = { textLayoutResult ->
                if (textLayoutResult.hasVisualOverflow) {
                    val firstTitleWidth = textLayoutResult.size.width.toFloat()
                    val firstTitleOffset = Offset(firstTitleWidth, 0f)
                    val firstTitleVisibleTextEndIndex = textLayoutResult.getOffsetForPosition(firstTitleOffset) + 1

                    secondTitleText = headerInfo.title.substring(firstTitleVisibleTextEndIndex)
                }
            },
            style = GamedgeTheme.typography.h6,
        )

        Box(modifier = Modifier.layoutId(ConstraintIdSecondTitle)) {
            if (isSecondTitleVisible) {
                // Remove font padding once https://issuetracker.google.com/issues/171394808
                // is implemented (includeFontPadding="false" in XML)
                Text(
                    text = secondTitleText,
                    color = GamedgeTheme.colors.onPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = GamedgeTheme.typography.h6,
                )
            }
        }

        Text(
            text = headerInfo.releaseDate,
            modifier = Modifier.layoutId(ConstraintIdReleaseDate),
            color = GamedgeTheme.colors.onSurface,
            style = GamedgeTheme.typography.subtitle3,
        )

        Box(modifier = Modifier.layoutId(ConstraintIdDeveloperName)) {
            if (headerInfo.hasDeveloperName) {
                Text(
                    text = checkNotNull(headerInfo.developerName),
                    color = GamedgeTheme.colors.onSurface,
                    style = GamedgeTheme.typography.subtitle3,
                )
            }
        }

        Info(
            icon = painterResource(CoreR.drawable.star_circle_outline),
            title = headerInfo.rating,
            modifier = Modifier.layoutId(ConstraintIdRating),
            iconSize = InfoIconSize,
            titleTextStyle = GamedgeTheme.typography.caption,
        )
        Info(
            icon = painterResource(CoreR.drawable.account_heart_outline),
            title = headerInfo.likeCount,
            modifier = Modifier.layoutId(ConstraintIdLikeCount),
            iconSize = InfoIconSize,
            titleTextStyle = GamedgeTheme.typography.caption,
        )
        Info(
            icon = painterResource(CoreR.drawable.age_rating_outline),
            title = headerInfo.ageRating,
            modifier = Modifier.layoutId(ConstraintIdAgeRating),
            iconSize = InfoIconSize,
            titleTextStyle = GamedgeTheme.typography.caption,
        )
        Info(
            icon = painterResource(CoreR.drawable.shape_outline),
            title = headerInfo.gameCategory,
            modifier = Modifier.layoutId(ConstraintIdGameCategory),
            iconSize = InfoIconSize,
            titleTextStyle = GamedgeTheme.typography.caption,
        )
    }
}

private class LikeButton(context: Context) : FloatingActionButton(context) {

    private companion object {
        const val STATE_CHECKED = android.R.attr.state_checked
        const val STATE_CHECKED_ON = (STATE_CHECKED * 1)
        const val STATE_CHECKED_OFF = (STATE_CHECKED * -1)
    }

    var isLiked: Boolean
        set(value) {
            setImageState(intArrayOf(if (value) STATE_CHECKED_ON else STATE_CHECKED_OFF), true)
        }
        get() = drawableState.contains(STATE_CHECKED_ON)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        // This is a hacky solution to fix a very strange case, where a user likes a game,
        // scrolls the view out of the screen or goes to a another screen (e.g., a related game)
        // and comes back, then the like button resets its icon from a filled heart to an empty
        // heart. To fix it, when this view gets reattached to the window, we are asking the button
        // to reset its state and then go to the liked state again.
        if (isLiked) {
            isLiked = false
            isLiked = true
        }
    }
}

@Composable
private fun constructExpandedConstraintSet(): ConstraintSet {
    val artworksHeight = 240.dp
    val pageIndicatorMargin = GamedgeTheme.spaces.spacing_2_5
    val coverSpaceMargin = CoverSpace
    val coverMarginStart = GamedgeTheme.spaces.spacing_3_5
    val likeBtnMarginEnd = GamedgeTheme.spaces.spacing_2_5
    val titleMarginStart = GamedgeTheme.spaces.spacing_3_5
    val firstTitleMarginTop = titleMarginStart
    val firstTitleMarginEnd = GamedgeTheme.spaces.spacing_1_0
    val secondTitleMarginEnd = GamedgeTheme.spaces.spacing_3_5
    val releaseDateMarginTop = GamedgeTheme.spaces.spacing_2_5
    val releaseDateMarginHorizontal = GamedgeTheme.spaces.spacing_3_5
    val developerNameMarginHorizontal = GamedgeTheme.spaces.spacing_3_5
    val bottomBarrierMargin = GamedgeTheme.spaces.spacing_5_0
    val infoItemMarginBottom = GamedgeTheme.spaces.spacing_3_5

    return ConstraintSet {
        val artworks = createRefFor(ConstraintIdArtworks)
        val artworksScrim = createRefFor(ConstraintIdArtworksScrim)
        val backButton = createRefFor(ConstraintIdBackButton)
        val pageIndicator = createRefFor(ConstraintIdPageIndicator)
        val backdrop = createRefFor(ConstraintIdBackdrop)
        val coverSpace = createRefFor(ConstraintIdCoverSpace)
        val cover = createRefFor(ConstraintIdCover)
        val likeButton = createRefFor(ConstraintIdLikeButton)
        val firstTitle = createRefFor(ConstraintIdFirstTitle)
        val secondTitle = createRefFor(ConstraintIdSecondTitle)
        val releaseDate = createRefFor(ConstraintIdReleaseDate)
        val developerName = createRefFor(ConstraintIdDeveloperName)
        val bottomBarrier = createBottomBarrier(cover, developerName, margin = bottomBarrierMargin)
        val rating = createRefFor(ConstraintIdRating)
        val likeCount = createRefFor(ConstraintIdLikeCount)
        val ageRating = createRefFor(ConstraintIdAgeRating)
        val gameCategory = createRefFor(ConstraintIdGameCategory)

        constrain(artworks) {
            width = Dimension.fillToConstraints
            height = Dimension.value(artworksHeight)
            top.linkTo(parent.top)
            centerHorizontallyTo(parent)
        }
        constrain(artworksScrim) {
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
            centerVerticallyTo(artworks)
            centerHorizontallyTo(artworks)
        }
        constrain(backButton) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
        }
        constrain(pageIndicator) {
            top.linkTo(parent.top, pageIndicatorMargin)
            end.linkTo(parent.end, pageIndicatorMargin)
        }
        constrain(backdrop) {
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
            top.linkTo(artworks.bottom)
            bottom.linkTo(parent.bottom)
            centerHorizontallyTo(parent)
        }
        constrain(coverSpace) {
            start.linkTo(parent.start)
            bottom.linkTo(artworks.bottom, coverSpaceMargin)
        }
        constrain(cover) {
            top.linkTo(coverSpace.bottom)
            start.linkTo(parent.start, coverMarginStart)
        }
        constrain(likeButton) {
            top.linkTo(artworks.bottom)
            bottom.linkTo(artworks.bottom)
            end.linkTo(parent.end, likeBtnMarginEnd)
        }
        constrain(firstTitle) {
            width = Dimension.fillToConstraints
            top.linkTo(artworks.bottom, firstTitleMarginTop)
            start.linkTo(cover.end, titleMarginStart)
            end.linkTo(likeButton.start, firstTitleMarginEnd)
        }
        constrain(secondTitle) {
            width = Dimension.fillToConstraints
            top.linkTo(firstTitle.bottom)
            start.linkTo(cover.end, titleMarginStart)
            end.linkTo(parent.end, secondTitleMarginEnd)
        }
        constrain(releaseDate) {
            width = Dimension.fillToConstraints
            top.linkTo(secondTitle.bottom, releaseDateMarginTop)
            start.linkTo(cover.end, releaseDateMarginHorizontal)
            end.linkTo(parent.end, releaseDateMarginHorizontal)
        }
        constrain(developerName) {
            width = Dimension.fillToConstraints
            top.linkTo(releaseDate.bottom)
            start.linkTo(cover.end, developerNameMarginHorizontal)
            end.linkTo(parent.end, developerNameMarginHorizontal)
        }
        constrain(rating) {
            width = Dimension.fillToConstraints
            top.linkTo(bottomBarrier)
            bottom.linkTo(parent.bottom, infoItemMarginBottom)
            linkTo(start = parent.start, end = likeCount.start, bias = 0.25f)
        }
        constrain(likeCount) {
            width = Dimension.fillToConstraints
            top.linkTo(bottomBarrier)
            bottom.linkTo(parent.bottom, infoItemMarginBottom)
            linkTo(start = rating.end, end = ageRating.start, bias = 0.25f)
        }
        constrain(ageRating) {
            width = Dimension.fillToConstraints
            top.linkTo(bottomBarrier)
            bottom.linkTo(parent.bottom, infoItemMarginBottom)
            linkTo(start = likeCount.end, end = gameCategory.start, bias = 0.25f)
        }
        constrain(gameCategory) {
            width = Dimension.fillToConstraints
            top.linkTo(bottomBarrier)
            bottom.linkTo(parent.bottom, infoItemMarginBottom)
            linkTo(start = ageRating.end, end = parent.end, bias = 0.25f)
        }
    }
}

@PreviewLightDark
@Composable
private fun GameInfoHeaderPreview() {
    GamedgeTheme {
        GameInfoHeader(
            headerInfo = GameInfoHeaderUiModel(
                artworks = listOf(GameInfoArtworkUiModel.DefaultImage),
                isLiked = true,
                coverImageUrl = null,
                title = "Elden Ring",
                releaseDate = "Feb 25, 2022 (in a month)",
                developerName = "FromSoftware",
                rating = "N/A",
                likeCount = "92",
                ageRating = "N/A",
                gameCategory = "Main",
            ),
            onArtworkClicked = {},
            onBackButtonClicked = {},
            onCoverClicked = {},
            onLikeButtonClicked = {},
        )
    }
}
