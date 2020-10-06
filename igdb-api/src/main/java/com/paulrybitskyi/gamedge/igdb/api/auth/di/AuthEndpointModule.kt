/*
 * Copyright 2020 Paul Rybitskyi, paul.rybitskyi.work@gmail.com
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

package com.paulrybitskyi.gamedge.igdb.api.auth.di

import com.paulrybitskyi.gamedge.igdb.api.BuildConfig
import com.paulrybitskyi.gamedge.igdb.api.auth.AuthEndpoint
import com.paulrybitskyi.gamedge.igdb.api.auth.AuthEndpointImpl
import com.paulrybitskyi.gamedge.igdb.api.auth.AuthService
import com.paulrybitskyi.gamedge.igdb.api.auth.Constants
import com.paulrybitskyi.gamedge.igdb.api.commons.di.qualifiers.Endpoint
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
internal object AuthEndpointModule {


    @Singleton
    @Provides
    fun provideAuthEndpoint(authService: AuthService): AuthEndpoint {
        return AuthEndpointImpl(
            authService = authService,
            clientId = BuildConfig.TWITCH_APP_CLIENT_ID,
            clientSecret = BuildConfig.TWITCH_APP_CLIENT_SECRET
        )
    }


    @Provides
    fun provideAuthService(
        @Endpoint(Endpoint.Type.AUTH)
        retrofit: Retrofit
    ): AuthService {
        return retrofit.create(AuthService::class.java)
    }


    @Endpoint(Endpoint.Type.AUTH)
    @Provides
    fun provideRetrofit(
        retrofitBuilder: Retrofit.Builder,
        @Endpoint(Endpoint.Type.AUTH)
        moshi: Moshi
    ): Retrofit {
        return retrofitBuilder
            .baseUrl(Constants.TWITCH_API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }


    @Endpoint(Endpoint.Type.AUTH)
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }


}