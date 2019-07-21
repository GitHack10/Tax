package com.example.needtechnology.domain.auth

import com.example.needtechnology.data.auth.AuthRepositoryImpl
import com.example.needtechnology.data.global.local.PreferenceStorage
import com.example.needtechnology.domain.global.UserInfo
import com.example.needtechnology.domain.global.common.ui
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl,
    private val prefs: PreferenceStorage
) {

    fun signInRequest(authData: String) = authRepositoryImpl
        .signInRequest(authData)
        .observeOn(ui)


    fun saveUserInfo(userInfo: UserInfo) {
        prefs.username = userInfo.name ?: ""
        prefs.email = userInfo.email ?: ""
        prefs.phone = userInfo.phone ?: ""
        prefs.birth = userInfo.birth ?: ""
        prefs.gender= userInfo.gender ?: ""
    }

    fun setIsLogin(isLogin: Boolean) {
        prefs.isLogin = isLogin
    }
}