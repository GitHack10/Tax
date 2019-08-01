package ru.dagdelo.business05.domain.auth

import io.reactivex.Single
import ru.dagdelo.business05.data.global.local.PreferenceStorage
import ru.dagdelo.business05.domain.global.common.ui
import ru.dagdelo.business05.domain.global.models.UserInfo
import ru.dagdelo.business05.domain.global.models.UserReg
import ru.dagdelo.business05.domain.global.models.UserRegResponse
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val authRepositoryImpl: ru.dagdelo.business05.data.auth.AuthRepositoryImpl,
    private val prefs: PreferenceStorage
) {

    fun signInRequest(email: String, password: String) = authRepositoryImpl
        .signInRequest(email, password)
        .observeOn(ui)


    fun saveUserInfo(userInfo: UserInfo) {
        prefs.username = userInfo.name ?: ""
        prefs.email = userInfo.email ?: ""
        prefs.password = userInfo.password ?: ""
        prefs.phone = userInfo.phone ?: ""
        prefs.birth = userInfo.birth ?: ""
        prefs.gender = userInfo.gender.toString() ?: ""
    }

    fun setIsLogin(isLogin: Boolean) {
        prefs.isLogin = isLogin
    }

    fun getUserInfo() = UserInfo(
        name = prefs.username,
        email = prefs.email,
        password = prefs.password,
        phone = prefs.phone,
        birth = prefs.birth,
        gender = prefs.gender.toInt()
    )

    fun registerUser(userReg: UserReg): Single<UserRegResponse> =
        authRepositoryImpl.registerUser(userReg)
            .observeOn(ui)

    fun getPhone() = prefs.phone

    fun savePhone(phone: String, maskedPhone: String) {
        prefs.phone = phone
        prefs.maskedPhone = maskedPhone
    }

    fun saveToken(token: String) {
        prefs.token = token
    }

    fun signOut() {
        prefs.token = ""
        prefs.isLogin = false
    }
}