package com.example.needtechnology.domain.profile

import com.example.needtechnology.data.global.local.PreferenceStorage
import com.example.needtechnology.data.profile.ProfileRepositoryImpl
import com.example.needtechnology.domain.global.common.ui
import com.example.needtechnology.domain.global.models.User
import com.example.needtechnology.domain.global.models.UserInfo
import io.reactivex.Single
import javax.inject.Inject

class ProfileInteractor @Inject constructor(
    private val profileRepositoryImpl: ProfileRepositoryImpl,
    private val prefs: PreferenceStorage
) {

    fun getUserInfo(): Single<User> =
        profileRepositoryImpl.getUserInfo()
            .observeOn(ui)

//    fun getUserInfo() = UserInfo(
//        prefs.username,
//        prefs.maskedPhone,
//        prefs.email
////        prefs.birth,
////        prefs.gender
//    )

    fun saveUserInfo(username: String, email: String) {
        prefs.username = username
        prefs.email = email
    }

    fun clearUserData() {
        prefs.username = ""
        prefs.phone = ""
        prefs.email = ""
        prefs.password = ""
        prefs.maskedPhone = ""
        prefs.deviceId = ""
        prefs.birth = ""
        prefs.gender = ""
    }

    fun setIsLogin(isLogout: Boolean) {
        prefs.isLogin = isLogout
    }
}