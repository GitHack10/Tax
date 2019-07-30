package com.example.needtechnology.di.screens.auth.signin

import com.example.needtechnology.di.global.nameds.SIGN_IN_FLOW
import com.example.needtechnology.di.global.scopes.FlowFragmentScope
import com.example.needtechnology.presentation.global.navigation.FlowRouter
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Named

@Module
object SignInNavigationModule {

    @Provides
    @JvmStatic
    @FlowFragmentScope
    @Named(SIGN_IN_FLOW)
    fun provideCicerone(appRouter: Router): Cicerone<FlowRouter> =
        Cicerone.create(FlowRouter(appRouter))

    @Provides
    @JvmStatic
    @FlowFragmentScope
    @Named(SIGN_IN_FLOW)
    fun provideAppRouter(
        @Named(SIGN_IN_FLOW) cicerone: Cicerone<FlowRouter>
    ): FlowRouter = cicerone.router!!


    @Provides
    @JvmStatic
    @FlowFragmentScope
    @Named(SIGN_IN_FLOW)
    fun provideNavigatorHolder(
        @Named(SIGN_IN_FLOW) cicerone: Cicerone<FlowRouter>
    ) = cicerone.navigatorHolder!!
}