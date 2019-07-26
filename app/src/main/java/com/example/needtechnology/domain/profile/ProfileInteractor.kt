package com.example.needtechnology.domain.profile

import com.example.needtechnology.data.global.local.PreferenceStorage
import com.example.needtechnology.data.profile.ProfileRepositoryImpl
import com.example.needtechnology.domain.global.UserInfo
import javax.inject.Inject

class ProfileInteractor @Inject constructor(
    private val profileRepositoryImpl: ProfileRepositoryImpl,
    private val prefs: PreferenceStorage
) {

    fun getUserInfo() = UserInfo(
        prefs.username,
        prefs.phone,
        prefs.email,
        prefs.birth,
        prefs.gender
    )

    fun saveUserInfo(username: String, email: String) {
        prefs.username = username
        prefs.email = email
    }

    fun clearUserData() {
        prefs.username = ""
        prefs.phone = ""
        prefs.email = ""
        prefs.birth = ""
        prefs.gender = ""
    }

    fun setIsLogin(isLogout: Boolean) {
        prefs.isLogin = isLogout
    }
}