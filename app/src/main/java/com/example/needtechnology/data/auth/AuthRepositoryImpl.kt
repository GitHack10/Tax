package com.example.needtechnology.data.auth

import com.example.needtechnology.data.global.netwotk.ApiDagDelo
import com.example.needtechnology.data.global.netwotk.utils.createBasicAuthHeader
import com.example.needtechnology.domain.global.common.io
import com.example.needtechnology.domain.global.models.AuthResponse
import com.example.needtechnology.domain.global.models.UserReg
import com.example.needtechnology.domain.global.models.UserRegResponse
import com.example.needtechnology.domain.global.repositories.AuthRepository
import io.reactivex.Single
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiDagDelo: ApiDagDelo
) : AuthRepository {

    override fun signInRequest(email: String, password: String): Single<AuthResponse> =
        apiDagDelo.signInUser(createBasicAuthHeader(email, password))
            .subscribeOn(io)

    override fun registerUser(userReg: UserReg): Single<UserRegResponse> =
        apiDagDelo.signUpUser(userReg).subscribeOn(io)
}