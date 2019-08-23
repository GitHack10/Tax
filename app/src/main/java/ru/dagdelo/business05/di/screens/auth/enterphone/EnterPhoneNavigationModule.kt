package ru.dagdelo.business05.di.screens.auth.enterphone

import dagger.Module
import dagger.Provides
import ru.dagdelo.business05.di.global.nameds.ENTER_PHONE_FLOW
import ru.dagdelo.business05.di.global.scopes.FlowFragmentScope
import ru.dagdelo.business05.presentation.global.navigation.FlowRouter
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Named

@Module
object EnterPhoneNavigationModule {

    @Provides
    @JvmStatic
    @FlowFragmentScope
    @Named(ENTER_PHONE_FLOW)
    fun provideCicerone(appRouter: Router): Cicerone<FlowRouter> =
        Cicerone.create(FlowRouter(appRouter))

    @Provides
    @JvmStatic
    @FlowFragmentScope
    @Named(ENTER_PHONE_FLOW)
    fun provideAppRouter(
        @Named(ENTER_PHONE_FLOW) cicerone: Cicerone<FlowRouter>
    ): FlowRouter = cicerone.router!!

    @Provides
    @JvmStatic
    @FlowFragmentScope
    @Named(ENTER_PHONE_FLOW)
    fun provideNavigatorHolder(
        @Named(ENTER_PHONE_FLOW) cicerone: Cicerone<FlowRouter>
    ) = cicerone.navigatorHolder!!
}