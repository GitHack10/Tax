package ru.dagdelo.business05.data.global.netwotk

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*
import ru.dagdelo.business05.domain.global.models.CheckInfoEntity
import ru.dagdelo.business05.domain.global.models.LoginResponse
import ru.dagdelo.business05.domain.global.models.UserReg

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