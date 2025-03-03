/*
 * Copyright 2021 Paul Rybitskyi, oss@paulrybitskyi.com
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

package com.paulrybitskyi.gamedge.feature.info.presentation.widgets.header

import androidx.compose.runtime.Immutable
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.header.artworks.GameInfoArtworkUiModel

@Immutable
internal data class GameInfoHeaderUiModel(
    val artworks: List<GameInfoArtworkUiModel>,
    val isLiked: Boolean,
    val coverImageUrl: String?,
    val title: String,
    val releaseDate: String,
    val developerName: String?,
    val rating: String,
    val likeCount: String,
    val ageRating: String,
    val gameCategory: String,
) {

    val hasCoverImageUrl: Boolean
        get() = (coverImageUrl != null)

    val hasDeveloperName: Boolean
        get() = (developerName != null)
}
