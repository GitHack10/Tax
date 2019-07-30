package com.example.needtechnology.data.global.netwotk

import com.example.needtechnology.domain.global.models.AuthResponse
import com.example.needtechnology.domain.global.models.CheckInfoEntity
import com.example.needtechnology.domain.global.models.User
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiDagDelo {

    @Multipart
    @POST("/api/v1/user/sign-up")
    fun signUpUser(
        @Part("full_name") fullName: String,
        @Part("email") email: String,
        @Part("password") password: String,
        @Part("phone") phone: String,
        @Part("gender") gender: Int,
        @Part("birthday") birth: String
    ): Single<Response<ResponseBody>>

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
    fun getCheckList(): Single<List<CheckInfoEntity>>
}