package ru.dagdelo.business05.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import ru.dagdelo.business05.domain.global.models.CheckInfoEntity

@Database(entities = [CheckInfoEntity.Document.Receipt::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun checkDao(): ru.dagdelo.business05.data.database.CheckDao
}