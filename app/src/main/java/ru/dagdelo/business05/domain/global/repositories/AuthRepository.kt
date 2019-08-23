package ru.dagdelo.business05.domain.global.repositories

import io.reactivex.Completable
import io.reactivex.Single
import ru.dagdelo.business05.domain.global.models.AuthResponse
import ru.dagdelo.business05.domain.global.models.UserReg
import ru.dagdelo.business05.domain.global.models.UserRegResponse

interface AuthRepository {

    fun getSmsCode(phone: String): Completable

    fun auth(phone: String, smsCode: String): Single<AuthResponse>

    fun registerUser(userReg: UserReg): Single<UserRegResponse>
}