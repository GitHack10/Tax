package com.example.needtechnology.di.global.modules

import android.content.Context
import com.example.needtechnology.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

    @JvmStatic
    @Provides
    @Singleton
    fun provideContext(app: App): Context = app.applicationContext
}