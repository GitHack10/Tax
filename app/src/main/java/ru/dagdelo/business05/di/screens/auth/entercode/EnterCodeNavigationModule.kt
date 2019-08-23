package ru.dagdelo.business05.di.screens.auth.entercode

import dagger.Module
import dagger.Provides
import ru.dagdelo.business05.di.global.nameds.ENTER_CODE_FLOW
import ru.dagdelo.business05.di.global.scopes.FlowFragmentScope
import ru.dagdelo.business05.presentation.global.navigation.FlowRouter
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Named

@Module
object EnterCodeNavigationModule {

    @Provides
    @JvmStatic
    @FlowFragmentScope
    @Named(ENTER_CODE_FLOW)
    fun provideCicerone(appRouter: Router): Cicerone<FlowRouter> =
        Cicerone.create(FlowRouter(appRouter))

    @Provides
    @JvmStatic
    @FlowFragmentScope
    @Named(ENTER_CODE_FLOW)
    fun provideAppRouter(
        @Named(ENTER_CODE_FLOW) cicerone: Cicerone<FlowRouter>
    ): FlowRouter = cicerone.router!!

    @Provides
    @JvmStatic
    @FlowFragmentScope
    @Named(ENTER_CODE_FLOW)
    fun provideNavigatorHolder(
        @Named(ENTER_CODE_FLOW) cicerone: Cicerone<FlowRouter>
    ) = cicerone.navigatorHolder!!
}