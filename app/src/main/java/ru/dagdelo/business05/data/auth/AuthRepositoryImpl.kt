package ru.dagdelo.business05.data.auth

import io.reactivex.Completable
import io.reactivex.Single
import ru.dagdelo.business05.data.global.netwotk.ApiDagDelo
import ru.dagdelo.business05.domain.global.common.io
import ru.dagdelo.business05.domain.global.models.AuthResponse
import ru.dagdelo.business05.domain.global.models.UserReg
import ru.dagdelo.business05.domain.global.models.UserRegResponse
import ru.dagdelo.business05.domain.global.repositories.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiDagDelo: ApiDagDelo
) : AuthRepository {

    override fun registerUser(userReg: UserReg): Single<UserRegResponse> =
        apiDagDelo.signUpUser(userReg).subscribeOn(io)

    override fun getSmsCode(phone: String): Completable =
        apiDagDelo.getSmsCode(phone)
            .subscribeOn(io)

    override fun auth(phone: String, smsCode: String): Single<AuthResponse> =
        apiDagDelo.auth(phone, smsCode)
            .subscribeOn(io)

//    override fun auth(phone: String, smsCode: String): Single<AuthResponse> =
//        apiDagDelo.auth(createBasicAuthHeader(phone, smsCode))
//            .subscribeOn(io)
}