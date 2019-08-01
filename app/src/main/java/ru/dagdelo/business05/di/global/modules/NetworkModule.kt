package ru.dagdelo.business05.di.global.modules

import android.content.Context
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import ru.dagdelo.business05.data.global.BASE_URL_DAG_DELO
import ru.dagdelo.business05.data.global.netwotk.ApiDagDelo
import ru.dagdelo.business05.data.global.netwotk.interceptors.TokenInterceptor
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
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_DAG_DELO)
        .client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
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
    fun provideDagdeloApi(retrofit: Retrofit) =
        retrofit.create(ApiDagDelo::class.java)
}