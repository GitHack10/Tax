package com.example.needtechnology.domain.global.repositories

import com.example.needtechnology.domain.global.models.AuthResponse
import com.example.needtechnology.domain.global.models.LoginResponse
import com.example.needtechnology.domain.global.models.UserReg
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response

interface AuthRepository {

//    fun getSmsCode(phone: String): Completable
//    fun signInRequest(phone: String, password: String): Single<LoginResponse>
//    fun registerUser(userReg: UserReg): Completable

    fun signInRequest(email: String, password: String): Single<AuthResponse>

    fun registerUser(userReg: UserReg): Single<Response<ResponseBody>>
}