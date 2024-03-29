package ru.dagdelo.business05.di.app

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.dagdelo.business05.di.global.scopes.FlowFragmentScope
import ru.dagdelo.business05.di.global.scopes.FragmentScope
import ru.dagdelo.business05.di.screens.auth.entercode.EnterCodeNavigationModule
import ru.dagdelo.business05.di.screens.auth.enterphone.EnterPhoneNavigationModule
import ru.dagdelo.business05.di.screens.auth.signup.SignUpNavigationModule
import ru.dagdelo.business05.di.screens.checklist.ChecklistModule
import ru.dagdelo.business05.di.screens.home.HomeModule
import ru.dagdelo.business05.di.screens.home.sendcomplaint.SendComplaintNavigationModule
import ru.dagdelo.business05.di.screens.mainflow.MainFlowModule
import ru.dagdelo.business05.di.screens.mainflow.MainFlowNavigationModule
import ru.dagdelo.business05.di.screens.news.NewsModule
import ru.dagdelo.business05.di.screens.profile.ProfileModule
import ru.dagdelo.business05.presentation.screens.auth.entercode.ui.EnterCodeFragment
import ru.dagdelo.business05.presentation.screens.auth.enterphone.ui.EnterPhoneFragment
import ru.dagdelo.business05.presentation.screens.auth.signup.ui.SignUpFragment
import ru.dagdelo.business05.presentation.screens.checklist.ui.ChecklistFragment
import ru.dagdelo.business05.presentation.screens.sendcomplaint.ui.SendComplaintFragment
import ru.dagdelo.business05.presentation.screens.home.ui.HomeFragment
import ru.dagdelo.business05.presentation.screens.mainflow.ui.MainFlowFragment
import ru.dagdelo.business05.presentation.screens.news.ui.NewsFragment
import ru.dagdelo.business05.presentation.screens.profile.ui.ProfileFragment

/** Created by Kamil Abdulatipov on 22.06.2019. */

@Module
interface AppActivityModule {

    @FlowFragmentScope
    @ContributesAndroidInjector(modules = [EnterPhoneNavigationModule::class])
    fun contributeEnterPhoneFragment(): EnterPhoneFragment

    @FlowFragmentScope
    @ContributesAndroidInjector(modules = [EnterCodeNavigationModule::class])
    fun contributeEnterCodeFragment(): EnterCodeFragment

    @FlowFragmentScope
    @ContributesAndroidInjector(modules = [SignUpNavigationModule::class])
    fun contributeSignUpFragment(): SignUpFragment

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

    @FlowFragmentScope
    @ContributesAndroidInjector(modules = [SendComplaintNavigationModule::class])
    fun contributeSendComplaintFragment(): SendComplaintFragment
}