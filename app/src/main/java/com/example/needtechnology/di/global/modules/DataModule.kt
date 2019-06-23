package com.example.needtechnology.di.global.modules

import com.example.needtechnology.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/** Created by Kamil Abdulatipov on 22.06.2019. */

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideDatabase(appDatabase: AppDatabase) = appDatabase
}