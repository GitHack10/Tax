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
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

class AuthRepositoryImpl @Inject constructor(
    @Named(DAGDELO_API) private val apiDagDelo: ApiDagDelo
): AuthRepository {

    override fun signInRequest(email: String, password: String): Single<AuthResponse> =
        apiDagDelo.signInUser(createBasicAuthHeader(email, password))
            .subscribeOn(io)

    override fun registerUser(userReg: UserReg): Single<Response<ResponseBody>> =
        apiDagDelo.signUpUser(
            fullName = userReg.name,
            email = userReg.email,
            password = userReg.password,
            phone = userReg.phone,
            birth = userReg.birth,
            gender = userReg.gender
        ).subscribeOn(io)
}