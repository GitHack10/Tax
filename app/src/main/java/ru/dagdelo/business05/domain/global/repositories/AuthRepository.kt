package ru.dagdelo.business05.domain.global.repositories

import io.reactivex.Single
import ru.dagdelo.business05.domain.global.models.AuthResponse
import ru.dagdelo.business05.domain.global.models.UserReg
import ru.dagdelo.business05.domain.global.models.UserRegResponse

interface AuthRepository {

    fun signInRequest(email: String, password: String): Single<AuthResponse>

    fun registerUser(userReg: UserReg): Single<UserRegResponse>
}