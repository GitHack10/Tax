package com.example.needtechnology.di.screens.auth.phoneinput

import com.example.needtechnology.di.global.nameds.*
import com.example.needtechnology.di.global.scopes.FlowFragmentScope
import com.example.needtechnology.presentation.global.navigation.FlowRouter
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Named

@Module
object PhoneInputNavigationModule {

    @Provides
    @JvmStatic
    @FlowFragmentScope
    @Named(PHONE_INPUT_FLOW)
    fun provideCicerone(appRouter: Router): Cicerone<FlowRouter> =
        Cicerone.create(FlowRouter(appRouter))

    @Provides
    @JvmStatic
    @FlowFragmentScope
    @Named(PHONE_INPUT_FLOW)
    fun provideAppRouter(
        @Named(PHONE_INPUT_FLOW) cicerone: Cicerone<FlowRouter>
    ): FlowRouter = cicerone.router!!


    @Provides
    @JvmStatic
    @FlowFragmentScope
    @Named(PHONE_INPUT_FLOW)
    fun provideNavigatorHolder(
        @Named(PHONE_INPUT_FLOW) cicerone: Cicerone<FlowRouter>
    ) = cicerone.navigatorHolder!!
}