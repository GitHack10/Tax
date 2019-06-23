package com.example.needtechnology.di.global.modules

import com.example.needtechnology.data.global.NEEDTECHNOLOGY_BASE_URL
import com.example.needtechnology.data.global.netwotk.ApiNeedTechnology
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Singleton

/** Created by Kamil Abdulatipov on 22.06.2019. */

@Module
object NetworkModule {

    private val gson: GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    @JvmStatic
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
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
        .baseUrl(NEEDTECHNOLOGY_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(SimpleXmlConverterFactory.createNonStrict())
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
    fun provideNeedTechnologyApi(retrofit: Retrofit) = retrofit.create(ApiNeedTechnology::class.java)
}