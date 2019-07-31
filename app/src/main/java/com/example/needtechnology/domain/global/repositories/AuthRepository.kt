package com.example.needtechnology.domain.global.repositories

import com.example.needtechnology.domain.global.models.AuthResponse
import com.example.needtechnology.domain.global.models.UserReg
import com.example.needtechnology.domain.global.models.UserRegResponse
import io.reactivex.Single

interface AuthRepository {

//    fun getSmsCode(phone: String): Completable
//    fun signInRequest(phone: String, password: String): Single<LoginResponse>
//    fun registerUser(userReg: UserReg): Completable

    fun signInRequest(email: String, password: String): Single<AuthResponse>

    fun registerUser(userReg: UserReg): Single<UserRegResponse>
}