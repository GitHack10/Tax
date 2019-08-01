package ru.dagdelo.business05.domain.app

import ru.dagdelo.business05.data.global.local.PreferenceStorage
import javax.inject.Inject

class AppInteractor @Inject constructor(
    private val appRepositoryImpl: ru.dagdelo.business05.data.app.AppRepositoryImpl,
    private val prefs: PreferenceStorage
) {

    fun getPhone() = prefs.phone

    fun isLogin() = prefs.isLogin
    fun saveDeviceId(deviceId: String) {
        prefs.deviceId = deviceId
    }
}