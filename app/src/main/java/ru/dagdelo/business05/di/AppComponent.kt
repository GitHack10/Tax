package ru.dagdelo.business05.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ru.dagdelo.business05.App
import ru.dagdelo.business05.di.global.modules.*
import javax.inject.Singleton

/**
 * Created by Kamil Abdulatipov on 22.06.2019.
 * telegram: @mr_geeek
 * */

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppActivityBindingModule::class,
        AppModule::class,
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