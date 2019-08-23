package ru.dagdelo.business05.data.global.netwotk

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*
import ru.dagdelo.business05.domain.global.models.*

interface ApiDagDelo {

    @POST("/api/v1/user/sign-up")
    fun signUpUser(@Body userReg: UserReg): Single<UserRegResponse>

    @GET("/api/v1/user/auth")
    fun signInUser(
        @Header("Authorization") authData: String
    ): Single<AuthResponse>

    @FormUrlEncoded
    @POST("/api/v2/main/sendsms")
    fun getSmsCode(@Field("phone") phone: String): Completable

//    @FormUrlEncoded
//    @POST("/api/v2/main/checkcode")
//    fun auth(
//        @Field("phone") phone: String,
//        @Field("code") smsCode: String
//    ): Single<AuthResponse>

    @POST("/api/v2/main/checkcode")
    fun auth(@Header("Authorization") basicAuthHeader: String): Single<AuthResponse>

    @GET("/api/v1/check/index")
    fun prepareCheck(
        @Query("fn") fn: String,
        @Query("i") fd: String,
        @Query("fp") fpd: String,
        @Query("t") date: String,
        @Query("n") type: Int,
        @Query("s") sum: String
    ): Completable

    @POST("/api/v1/profile/info")
    fun getUserInfo(): Single<User>

    @GET("/api/v1/check/my-checks")
    fun getCheckList(): Single<List<CheckInfo>>

    @POST("/api/v1/profile/edit")
    fun editProfile(@Body editProfile: EditProfile): Completable
}