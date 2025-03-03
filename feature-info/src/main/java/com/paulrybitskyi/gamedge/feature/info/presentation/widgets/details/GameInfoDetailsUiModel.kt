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

package com.paulrybitskyi.gamedge.feature.info.presentation.widgets.details

import androidx.compose.runtime.Immutable

@Immutable
internal data class GameInfoDetailsUiModel(
    val genresText: String?,
    val platformsText: String?,
    val modesText: String?,
    val playerPerspectivesText: String?,
    val themesText: String?,
) {

    val hasGenresText: Boolean
        get() = (genresText != null)

    val hasPlatformsText: Boolean
        get() = (platformsText != null)

    val hasModesText: Boolean
        get() = (modesText != null)

    val hasPlayerPerspectivesText: Boolean
        get() = (playerPerspectivesText != null)

    val hasThemesText: Boolean
        get() = (themesText != null)
}
