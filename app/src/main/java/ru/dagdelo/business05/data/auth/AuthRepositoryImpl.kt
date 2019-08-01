package ru.dagdelo.business05.data.auth

import io.reactivex.Single
import ru.dagdelo.business05.data.global.netwotk.ApiDagDelo
import ru.dagdelo.business05.data.global.netwotk.utils.createBasicAuthHeader
import ru.dagdelo.business05.domain.global.common.io
import ru.dagdelo.business05.domain.global.models.AuthResponse
import ru.dagdelo.business05.domain.global.models.UserReg
import ru.dagdelo.business05.domain.global.models.UserRegResponse
import ru.dagdelo.business05.domain.global.repositories.AuthRepository
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