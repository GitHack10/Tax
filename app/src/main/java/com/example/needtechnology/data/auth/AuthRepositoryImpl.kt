package com.example.needtechnology.data.auth

import com.example.needtechnology.data.global.netwotk.ApiDagDelo
import com.example.needtechnology.data.global.netwotk.utils.createBasicAuthHeader
import com.example.needtechnology.di.global.nameds.DAGDELO_API
import com.example.needtechnology.domain.global.common.io
import com.example.needtechnology.domain.global.models.AuthResponse
import com.example.needtechnology.domain.global.models.UserReg
import com.example.needtechnology.domain.global.repositories.AuthRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class AuthRepositoryImpl @Inject constructor(
    @Named(DAGDELO_API) private val apiDagDelo: ApiDagDelo
): AuthRepository {

    override fun signInRequest(email: String, password: String): Single<AuthResponse> =
        apiDagDelo.signInUser(createBasicAuthHeader(email, password))
            .subscribeOn(io)

    override fun registerUser(userReg: UserReg): Completable =
        apiDagDelo.signUpUser(
            fullName = userReg.name,
            email = userReg.email,
            password = userReg.password,
            phone = userReg.phone,
            birth = userReg.birth,
            gender = userReg.gender
        ).subscribeOn(io)

//    override fun getSmsCode(phone: String): Completable =
//        apiBusinessDag.getSmsCode(hashMapOf("phone" to phone))
//            .subscribeOn(io)
//
//    override fun signInRequest(phone: String, password: String) =
//        apiBusinessDag.loginRequest(createBasicAuthHeader(phone, password))
//            .subscribeOn(io)
//
//    override fun registerUser(userReg: UserReg): Completable =
//        apiBusinessDag.registerUser(userReg)
//            .subscribeOn(io)


}