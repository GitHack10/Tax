package ru.dagdelo.business05.di.screens.home.sendcomplaint

import dagger.Module
import dagger.Provides
import ru.dagdelo.business05.di.global.nameds.ENTER_CODE_FLOW
import ru.dagdelo.business05.di.global.nameds.SEND_COMPLAINT_FLOW
import ru.dagdelo.business05.di.global.scopes.FlowFragmentScope
import ru.dagdelo.business05.presentation.global.navigation.FlowRouter
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Named

@Module
object SendComplaintNavigationModule {

    @Provides
    @JvmStatic
    @FlowFragmentScope
    @Named(SEND_COMPLAINT_FLOW)
    fun provideCicerone(appRouter: Router): Cicerone<FlowRouter> =
        Cicerone.create(FlowRouter(appRouter))

    @Provides
    @JvmStatic
    @FlowFragmentScope
    @Named(SEND_COMPLAINT_FLOW)
    fun provideAppRouter(
        @Named(SEND_COMPLAINT_FLOW) cicerone: Cicerone<FlowRouter>
    ): FlowRouter = cicerone.router!!

    @Provides
    @JvmStatic
    @FlowFragmentScope
    @Named(SEND_COMPLAINT_FLOW)
    fun provideNavigatorHolder(
        @Named(SEND_COMPLAINT_FLOW) cicerone: Cicerone<FlowRouter>
    ) = cicerone.navigatorHolder!!
}