package com.example.needtechnology.domain.auth

import com.example.needtechnology.data.auth.AuthRepositoryImpl
import com.example.needtechnology.data.global.local.PreferenceStorage
import com.example.needtechnology.domain.global.common.ui
import com.example.needtechnology.domain.global.models.UserInfo
import com.example.needtechnology.domain.global.models.UserReg
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl,
    private val prefs: PreferenceStorage
) {

//    fun getSmsCode(phone: String): Completable =
//        authRepositoryImpl.getSmsCode(phone)
//            .observeOn(ui)

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

    fun registerUser(userReg: UserReg): Single<Response<ResponseBody>> =
        authRepositoryImpl.registerUser(userReg)
            .observeOn(ui)

    fun getPhone() = prefs.phone

    fun savePhone(phone: String, maskedPhone: String) {
        prefs.phone = phone
        prefs.maskedPhone = maskedPhone
    }

    fun getMaskedPhone() = prefs.maskedPhone

    fun saveToken(token: String) {
        prefs.token = token
    }
}