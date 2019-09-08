package com.machioni.mpvmovies.common.di

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.machioni.mpvmovies.BuildConfig
import com.machioni.mpvmovies.data.remote.datasource.MoviesRemoteDataSource
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule{
    @Provides
    fun okHttpClient() : OkHttpClient {
        return OkHttpClient.Builder().apply {
            if(BuildConfig.DEBUG)
                interceptors().add(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        }.build()
    }

    @Provides
    @Singleton
    fun retrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun moviesRemoteDataSource(retrofit: Retrofit) = retrofit.create(MoviesRemoteDataSource::class.java)
}