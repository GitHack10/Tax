package ru.dagdelo.business05.di.screens.auth.signup

import dagger.Module
import dagger.Provides
import ru.dagdelo.business05.di.global.nameds.SIGN_UP_FLOW
import ru.dagdelo.business05.di.global.scopes.FlowFragmentScope
import ru.dagdelo.business05.presentation.global.navigation.FlowRouter
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Named

@Module
object SignUpNavigationModule {

    @Provides
    @JvmStatic
    @FlowFragmentScope
    @Named(SIGN_UP_FLOW)
    fun provideCicerone(appRouter: Router): Cicerone<FlowRouter> =
        Cicerone.create(FlowRouter(appRouter))

    @Provides
    @JvmStatic
    @FlowFragmentScope
    @Named(SIGN_UP_FLOW)
    fun provideAppRouter(
        @Named(SIGN_UP_FLOW) cicerone: Cicerone<FlowRouter>
    ): FlowRouter = cicerone.router!!


    @Provides
    @JvmStatic
    @FlowFragmentScope
    @Named(SIGN_UP_FLOW)
    fun provideNavigatorHolder(
        @Named(SIGN_UP_FLOW) cicerone: Cicerone<FlowRouter>
    ): NavigatorHolder = cicerone.navigatorHolder
}