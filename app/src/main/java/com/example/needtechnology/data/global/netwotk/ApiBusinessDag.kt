package com.example.needtechnology.data.global.netwotk

import com.example.needtechnology.domain.global.models.CheckInfoEntity
import com.example.needtechnology.domain.global.models.LoginResponse
import com.example.needtechnology.domain.global.models.UserReg
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface ApiBusinessDag {

    @GET("/v1/mobile/users/login")
    fun loginRequest(
        @Header("Authorization") authData: String
    ): Single<LoginResponse>

    @POST("/v1/mobile/users/signup")
    fun registerUser(@Body userReg: UserReg): Completable

    @POST("/v1/mobile/users/restore")
    fun getSmsCode(@Body phone: HashMap<String, String>): Completable

    @GET("/v1/inns/*/kkts/*/fss/{fss}/tickets/{tickets}?sendToEmail=no")
    fun prepareCheck(
        @Header("Authorization") authData: String,
        @Header("Device-Id") deviceId: String,
        @Header("Device-OS") deviceOS: String = "android",
        @Path("fss") fss: String,
        @Path("tickets") tickets: String,
        @Query("fiscalSign") fiscalSign: String
    ): Single<CheckInfoEntity>
}