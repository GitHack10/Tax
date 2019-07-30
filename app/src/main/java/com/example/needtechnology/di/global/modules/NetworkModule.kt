package com.example.needtechnology.di.global.modules

import android.content.Context
import com.example.needtechnology.data.global.BASE_URL
import com.example.needtechnology.data.global.BASE_URL_DAG_DELO
import com.example.needtechnology.data.global.netwotk.ApiBusinessDag
import com.example.needtechnology.data.global.netwotk.ApiDagDelo
import com.example.needtechnology.data.global.netwotk.interceptors.TokenInterceptor
import com.example.needtechnology.di.global.nameds.DAGDELO_API
import com.example.needtechnology.di.global.nameds.NALOG_API
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/** Created by Kamil Abdulatipov on 22.06.2019. */

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpCache(context: Context) = Cache(
        context.cacheDir,
        10 * 1024 * 1024 // 10 MiB
    )

    @Provides
    @Singleton
    fun provideInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        cache: Cache,
        tokenInterceptor: TokenInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(tokenInterceptor)
        .addInterceptor(loggingInterceptor)
        .cache(cache)
        .build()

    @Provides
    @Singleton
    @Named(NALOG_API)
    fun provideNalogRetrofit(
        okHttpClient: OkHttpClient,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(rxJava2CallAdapterFactory)
        .build()

    @Provides
    @Singleton
    @Named(DAGDELO_API)
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_DAG_DELO)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(rxJava2CallAdapterFactory)
        .build()

    @Provides
    @Singleton
    fun provideRxJavaAdapter(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    @Singleton
    @Named(NALOG_API)
    fun provideTaxApi(@Named(NALOG_API) retrofit: Retrofit) =
        retrofit.create(ApiBusinessDag::class.java)

    @Provides
    @Singleton
    @Named(DAGDELO_API)
    fun provideDagdeloApi(@Named(DAGDELO_API) retrofit: Retrofit) =
        retrofit.create(ApiDagDelo::class.java)
}