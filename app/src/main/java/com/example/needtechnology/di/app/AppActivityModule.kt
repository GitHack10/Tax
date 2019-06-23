package com.example.needtechnology.di.app

import com.example.needtechnology.di.global.scopes.FlowFragmentScope
import com.example.needtechnology.di.global.scopes.FragmentScope
import com.example.needtechnology.di.screens.auth.passwordinput.PasswordInputNavigationModule
import com.example.needtechnology.di.screens.auth.phoneinput.PhoneInputNavigationModule
import com.example.needtechnology.di.screens.checklist.ChecklistModule
import com.example.needtechnology.di.screens.home.HomeModule
import com.example.needtechnology.di.screens.mainflow.MainFlowModule
import com.example.needtechnology.di.screens.mainflow.MainFlowNavigationModule
import com.example.needtechnology.di.screens.news.NewsModule
import com.example.needtechnology.di.screens.profile.ProfileModule
import com.example.needtechnology.presentation.screens.auth.passwordinput.ui.PasswordInputFragment
import com.example.needtechnology.presentation.screens.auth.phoneinput.ui.PhoneInputFragment
import com.example.needtechnology.presentation.screens.checklist.ui.ChecklistFragment
import com.example.needtechnology.presentation.screens.home.ui.HomeFragment
import com.example.needtechnology.presentation.screens.mainflow.ui.MainFlowFragment
import com.example.needtechnology.presentation.screens.news.ui.NewsFragment
import com.example.needtechnology.presentation.screens.profile.ui.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/** Created by Kamil Abdulatipov on 22.06.2019. */

@Module
interface AppActivityModule {

    @FlowFragmentScope
    @ContributesAndroidInjector(modules = [PhoneInputNavigationModule::class])
    fun contributePhoneInputFragment(): PhoneInputFragment

    @FlowFragmentScope
    @ContributesAndroidInjector(modules = [PasswordInputNavigationModule::class])
    fun contributePasswordInputFragment(): PasswordInputFragment

    @FlowFragmentScope
    @ContributesAndroidInjector(modules = [MainFlowModule::class, MainFlowNavigationModule::class])
    fun contributeMainFlowFragment(): MainFlowFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [HomeModule::class, MainFlowNavigationModule::class])
    fun contributeHomeFragment(): HomeFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [ChecklistModule::class, MainFlowNavigationModule::class])
    fun contributeChecklistFragment(): ChecklistFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [NewsModule::class, MainFlowNavigationModule::class])
    fun contributeNewsFragment(): NewsFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [ProfileModule::class, MainFlowNavigationModule::class])
    fun contributeProfileFragment(): ProfileFragment
}