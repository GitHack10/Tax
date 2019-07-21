package com.example.needtechnology.di.global.modules

import android.content.Context
import com.example.needtechnology.data.global.BASE_URL
import com.example.needtechnology.data.global.netwotk.ApiBusinessDag
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/** Created by Kamil Abdulatipov on 22.06.2019. */

@Module
object NetworkModule {

    @Provides
    @Singleton
    @JvmStatic
    fun provideOkHttpCache(context: Context) = Cache(
        context.cacheDir,
        10 * 1024 * 1024 // 10 MiB
    )

    @Provides
    @Singleton
    @JvmStatic
    fun provideOkHttpClient(cache: Cache, interceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .cache(cache)
        .build()

    @Provides
    @Singleton
    @JvmStatic
    fun provideInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideRetrofit(
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
    @JvmStatic
    fun provideRxJavaAdapter(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideTaxApi(retrofit: Retrofit) = retrofit.create(ApiBusinessDag::class.java)
}