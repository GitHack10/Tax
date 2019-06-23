package com.example.needtechnology.di.global.modules

import dagger.Module
import dagger.Provides
import com.example.needtechnology.presentation.global.navigation.AppRouter
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Singleton

@Module
object NavigationModule {

    @Provides
    @JvmStatic
    @Singleton
    fun provideCicerone(): Cicerone<AppRouter> = Cicerone.create(AppRouter())

    @Provides
    @JvmStatic
    @Singleton
    fun provideAppRouter(cicerone: Cicerone<AppRouter>): AppRouter = cicerone.router

    @Provides
    @JvmStatic
    @Singleton
    fun provideNavigatorHolder(
        cicerone: Cicerone<AppRouter>
    ): NavigatorHolder = cicerone.navigatorHolder
}