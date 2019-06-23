package com.example.needtechnology.di.global.modules

import com.example.needtechnology.di.app.AppActivityModule
import com.example.needtechnology.di.global.scopes.ActivityScope
import com.example.needtechnology.presentation.screens.app.ui.AppActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/** Created by Kamil Abdulatipov on 22.06.2019. */

@Module
interface AppActivityBindingModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [AppActivityModule::class])
    fun contributeAppActivity(): AppActivity
}