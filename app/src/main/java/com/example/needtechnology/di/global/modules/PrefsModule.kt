package com.example.needtechnology.di.global.modules

import com.example.needtechnology.data.global.local.PreferenceStorage
import com.example.needtechnology.data.global.local.SharedPreferenceStorage
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class PrefsModule {

    @Binds
    @Singleton
    abstract fun provideSharedPreference(storage: SharedPreferenceStorage): PreferenceStorage
}