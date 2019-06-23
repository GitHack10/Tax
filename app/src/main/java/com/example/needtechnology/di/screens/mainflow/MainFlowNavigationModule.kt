package com.example.needtechnology.di.screens.mainflow

import com.example.needtechnology.di.global.nameds.MAIN_FLOW
import com.example.needtechnology.di.global.scopes.FlowFragmentScope
import com.example.needtechnology.presentation.global.navigation.FlowRouter
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Named

@Module
object MainFlowNavigationModule {

    @Provides
    @JvmStatic
    @FlowFragmentScope
    @Named(MAIN_FLOW)
    fun provideCicerone(appRouter: Router): Cicerone<FlowRouter> =
        Cicerone.create(FlowRouter(appRouter))

    @Provides
    @JvmStatic
    @FlowFragmentScope
    @Named(MAIN_FLOW)
    fun provideAppRouter(
        @Named(MAIN_FLOW) cicerone: Cicerone<FlowRouter>
    ): FlowRouter = cicerone.router!!


    @Provides
    @JvmStatic
    @FlowFragmentScope
    @Named(MAIN_FLOW)
    fun provideNavigatorHolder(
        @Named(MAIN_FLOW) cicerone: Cicerone<FlowRouter>
    ) = cicerone.navigatorHolder!!
}