package com.example.needtechnology.domain.home

import com.example.needtechnology.data.global.local.PreferenceStorage
import com.example.needtechnology.data.home.HomeRepositoryImpl
import com.example.needtechnology.domain.global.common.io
import com.example.needtechnology.domain.global.common.ui
import com.example.needtechnology.domain.global.models.Check
import com.example.needtechnology.domain.global.models.CheckInfoEntity
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class HomeInteractor @Inject constructor(
    private val homeRepositoryImpl: HomeRepositoryImpl,
    private val prefs: PreferenceStorage
) {
    fun prepareCheck(check: Check): Completable =
        homeRepositoryImpl.prepareCheck(check)
            .observeOn(ui)

    fun getPhone() = prefs.phone

    fun getPassword() = prefs.password

    fun insertCheckInDB(checkInfo: CheckInfoEntity.Document.Receipt): Completable =
        homeRepositoryImpl.insertCheck(checkInfo)
}
