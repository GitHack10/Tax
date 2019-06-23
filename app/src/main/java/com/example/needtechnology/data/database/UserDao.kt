package com.example.needtechnology.data.database

import android.arch.persistence.room.*
import io.reactivex.Flowable

@Dao
interface UserDao {

    @Query("SELECT * FROM users_list")
    fun getAllUsers(): Flowable<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(food: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(food: List<UserEntity>)

    @Delete
    fun deleteUser(food: UserEntity)

    @Query("DELETE FROM users_list")
    fun deleteAllUsers()
}