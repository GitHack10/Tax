package ru.dagdelo.business05.di.global.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.dagdelo.business05.di.app.AppActivityModule
import ru.dagdelo.business05.di.global.scopes.ActivityScope
import ru.dagdelo.business05.presentation.screens.app.ui.AppActivity

/** Created by Kamil Abdulatipov on 22.06.2019. */

@Module
interface AppActivityBindingModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [AppActivityModule::class])
    fun contributeAppActivity(): AppActivity
}