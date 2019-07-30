package com.example.needtechnology.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.needtechnology.domain.global.models.CheckInfoEntity

@Database(entities = [CheckInfoEntity.Document.Receipt::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun checkDao(): CheckDao
}