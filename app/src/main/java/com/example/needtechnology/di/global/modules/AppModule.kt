package com.example.needtechnology.di.global.modules

import android.content.Context
import com.example.needtechnology.App
import com.example.needtechnology.data.global.local.PreferenceStorage
import com.example.needtechnology.domain.ResourceManager
import com.example.needtechnology.presentation.global.utils.ErrorHandler
import com.example.needtechnology.presentation.global.utils.NetworkErrorParser
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Module
object AppModule {

    @JvmStatic
    @Provides
    @Singleton
    fun provideContext(app: App): Context = app.applicationContext

    @JvmStatic
    @Provides
    @Singleton
    fun provideErrorHandler(
        router: Router,
        networkErrorParser: NetworkErrorParser,
        resourceManager: ResourceManager,
        prefs: PreferenceStorage
    ) = ErrorHandler(router, networkErrorParser, resourceManager, prefs)
}