package ru.dagdelo.business05.di.global.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.dagdelo.business05.data.global.local.PreferenceStorage
import ru.dagdelo.business05.domain.ResourceManager
import ru.dagdelo.business05.presentation.global.utils.ErrorHandler
import ru.dagdelo.business05.presentation.global.utils.NetworkErrorParser
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Module
object AppModule {

    @JvmStatic
    @Provides
    @Singleton
    fun provideContext(app: ru.dagdelo.business05.App): Context = app.applicationContext

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