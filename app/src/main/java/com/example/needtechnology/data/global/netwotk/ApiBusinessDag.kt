package com.example.needtechnology.data.global.netwotk

import com.example.needtechnology.domain.global.LoginResponse
import com.squareup.okhttp.RequestBody
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiBusinessDag {

    @GET("/v1/mobile/users/login")
    fun loginRequest(
        @Header("Authorization") authData: String
    ): Single<LoginResponse>

    @POST("/v1/mobile/users/signup")
    fun registrationRequest(@Body requestBody: RequestBody): Completable


}