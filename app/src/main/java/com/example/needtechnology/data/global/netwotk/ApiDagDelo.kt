package com.example.needtechnology.data.global.netwotk

import com.example.needtechnology.domain.global.models.*
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface ApiDagDelo {

    @POST("/api/v1/user/sign-up")
    fun signUpUser(@Body userReg: UserReg): Single<UserRegResponse>

    @GET("/api/v1/user/auth")
    fun signInUser(
        @Header("Authorization") authData: String
    ): Single<AuthResponse>

    @GET("/api/v1/check/index")
    fun prepareCheck(
        @Query("fn") fn: String,
        @Query("fd") fd: String,
        @Query("fpd") fpd: String
    ): Completable

    @POST("/api/v1/profile/info")
    fun getUserInfo(): Single<User>

    @GET("/api/v1/check/my-checks")
    fun getCheckList(): Single<List<CheckInfo>>
}