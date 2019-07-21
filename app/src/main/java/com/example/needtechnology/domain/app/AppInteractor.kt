package com.example.needtechnology.domain.app

import com.example.needtechnology.data.app.AppRepositoryImpl
import com.example.needtechnology.data.global.local.PreferenceStorage
import javax.inject.Inject

class AppInteractor @Inject constructor(
    private val appRepositoryImpl: AppRepositoryImpl,
    private val prefs: PreferenceStorage
) {

    fun getPhone() = prefs.phone

    fun isLogin() = prefs.isLogin
}