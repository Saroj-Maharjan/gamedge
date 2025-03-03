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

package com.paulrybitskyi.gamedge.common.domain.common.entities

private const val DEFAULT_PAGE_SIZE = 20

data class Pagination(
    val offset: Int = 0,
    val limit: Int = DEFAULT_PAGE_SIZE,
)

fun Pagination.hasDefaultLimit(): Boolean {
    return (limit == DEFAULT_PAGE_SIZE)
}

fun Pagination.nextOffset(): Pagination {
    return copy(offset = (offset + DEFAULT_PAGE_SIZE))
}

fun Pagination.nextLimit(): Pagination {
    return copy(limit = (limit + DEFAULT_PAGE_SIZE))
}
