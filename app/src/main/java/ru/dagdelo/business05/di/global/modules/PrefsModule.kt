package ru.dagdelo.business05.di.global.modules

import dagger.Binds
import dagger.Module
import ru.dagdelo.business05.data.global.local.PreferenceStorage
import ru.dagdelo.business05.data.global.local.SharedPreferenceStorage
import javax.inject.Singleton

@Module
abstract class PrefsModule {

    @Binds
    @Singleton
    abstract fun provideSharedPreference(storage: SharedPreferenceStorage): PreferenceStorage
}