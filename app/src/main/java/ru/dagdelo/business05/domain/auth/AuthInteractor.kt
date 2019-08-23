package ru.dagdelo.business05.domain.auth

import io.reactivex.Single
import ru.dagdelo.business05.data.global.local.PreferenceStorage
import ru.dagdelo.business05.domain.global.common.ui
import ru.dagdelo.business05.domain.global.models.AuthResponse
import ru.dagdelo.business05.domain.global.models.UserReg
import ru.dagdelo.business05.domain.global.models.UserRegResponse
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val authRepositoryImpl: ru.dagdelo.business05.data.auth.AuthRepositoryImpl,
    private val prefs: PreferenceStorage
) {

    fun getSmsCode(phone: String) = authRepositoryImpl
        .getSmsCode(phone)
        .observeOn(ui)

    fun auth(phone: String, smsCode: String): Single<AuthResponse> =
        authRepositoryImpl.auth(phone, smsCode)
            .observeOn(ui)

    fun registerUser(userReg: UserReg): Single<UserRegResponse> =
        authRepositoryImpl.registerUser(userReg)
            .observeOn(ui)

    fun setIsLogin(isLogin: Boolean, token: String) {
        prefs.isLogin = isLogin
        prefs.token = token
    }
}