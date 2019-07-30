package com.example.needtechnology.data.database

import android.arch.persistence.room.*
import com.example.needtechnology.domain.global.models.CheckInfoEntity
import io.reactivex.Flowable

@Dao
interface CheckDao {

    @Query("SELECT * FROM check_list")
    fun getAllChecks(): Flowable<List<CheckInfoEntity.Document.Receipt>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCheck(check: CheckInfoEntity.Document.Receipt)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChecks(checks: List<CheckInfoEntity.Document.Receipt>)

    @Delete
    fun deleteCheck(check: CheckInfoEntity.Document.Receipt)

    @Query("DELETE FROM check_list")
    fun deleteAllChecks()
}