package ru.dagdelo.business05.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ru.dagdelo.business05.di.global.modules.*
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
interface AppComponent : AndroidInjector<ru.dagdelo.business05.App> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<ru.dagdelo.business05.App>()
}