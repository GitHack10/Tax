package ru.dagdelo.business05.domain.profile

import io.reactivex.Completable
import io.reactivex.Single
import ru.dagdelo.business05.data.global.local.PreferenceStorage
import ru.dagdelo.business05.data.profile.ProfileRepositoryImpl
import ru.dagdelo.business05.domain.global.common.ui
import ru.dagdelo.business05.domain.global.models.User
import javax.inject.Inject

class ProfileInteractor @Inject constructor(
    private val profileRepositoryImpl: ProfileRepositoryImpl,
    private val prefs: PreferenceStorage
) {

    fun getUserInfo(): Single<User> =
        profileRepositoryImpl.getUserInfo()
            .observeOn(ui)

    fun editProfile(username: String): Completable =
        profileRepositoryImpl.editProfile(username)
            .observeOn(ui)

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

    fun cleanToken() {
        prefs.token = ""
    }
}