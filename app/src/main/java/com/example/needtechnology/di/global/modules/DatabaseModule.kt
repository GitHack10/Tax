package com.example.needtechnology.di.global.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.example.needtechnology.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/** Created by Kamil Abdulatipov on 29.07.2019. */

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideCheckDatabase(context: Context) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "dagdelo_db"
    )
        .fallbackToDestructiveMigration()
        .build()
}