package com.cheise_proj.parentapp.di.module.remote

import android.content.Context
import android.content.SharedPreferences
import com.cheise_proj.common_module.DEV_INFORDAS_BASE_URL
import com.cheise_proj.common_module.INFORDAS_BASE_URL
import com.cheise_proj.data.model.user.ProfileData
import com.cheise_proj.data.model.user.UserData
import com.cheise_proj.data.source.RemoteSource
import com.cheise_proj.parentapp.BuildConfig
import com.cheise_proj.parentapp.R
import com.cheise_proj.remote_source.RemoteSourceImpl
import com.cheise_proj.remote_source.api.ApiService
import com.cheise_proj.remote_source.mapper.ProfileDtoDataMapper
import com.cheise_proj.remote_source.mapper.RemoteMapper
import com.cheise_proj.remote_source.mapper.UserDtoDataMapper
import com.cheise_proj.remote_source.model.dto.IProfileDto
import com.cheise_proj.remote_source.model.dto.UserDto
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [RemoteModule.Binders::class])
class RemoteModule {
    @Module
    interface Binders {
        @Binds
        fun bindUserDtoDataMapper(userDtoDataMapper: UserDtoDataMapper): RemoteMapper<UserDto, UserData>

        @Binds
        fun bindRemoteSource(remoteSourceImpl: RemoteSourceImpl): RemoteSource

        @Binds
        fun bindProfileDtoDataMapper(profileDtoDataMapper: ProfileDtoDataMapper):
                RemoteMapper<IProfileDto, ProfileData>
    }

    @Suppress("SpellCheckingInspection")
    @Provides
    fun provideOkttpClient(tokenService: TokenService): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .addInterceptor(tokenService)
            .retryOnConnectionFailure(true)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(getBaseUrl())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    private fun getBaseUrl(): String {
        if (BuildConfig.DEBUG) return DEV_INFORDAS_BASE_URL
        return INFORDAS_BASE_URL
    }
}