package com.example.needtechnology.di

import com.example.needtechnology.App
import com.example.needtechnology.di.global.modules.*
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/** Created by Kamil Abdulatipov on 22.06.2019. */

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppActivityBindingModule::class,
        AppModule::class,
        DatabaseModule::class,
        DataModule::class,
        NavigationModule::class,
        NetworkModule::class,
        ResourceModule::class,
        PrefsModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}