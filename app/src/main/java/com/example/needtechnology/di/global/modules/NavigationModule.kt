package com.example.needtechnology.di.global.modules

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Module
object NavigationModule {

    @Provides
    @JvmStatic
    @Singleton
    fun provideCicerone(): Cicerone<Router> = Cicerone.create()

    @Provides
    @JvmStatic
    @Singleton
    fun provideAppRouter(cicerone: Cicerone<Router>): Router = cicerone.router


    @Provides
    @JvmStatic
    @Singleton
    fun provideNavigatorHolder(
        cicerone: Cicerone<Router>
    ): NavigatorHolder = cicerone.navigatorHolder
}