package ru.dagdelo.business05.domain.home

import io.reactivex.Completable
import ru.dagdelo.business05.data.global.local.PreferenceStorage
import ru.dagdelo.business05.data.home.HomeRepositoryImpl
import ru.dagdelo.business05.domain.global.common.ui
import ru.dagdelo.business05.domain.global.models.Check
import ru.dagdelo.business05.domain.global.models.CheckInfoEntity
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

    fun clearQrString() { prefs.qrString = "" }

    fun getQrString() = prefs.qrString
}
