package ru.dagdelo.business05.domain.checklist

import io.reactivex.Single
import ru.dagdelo.business05.data.global.local.PreferenceStorage
import ru.dagdelo.business05.domain.global.common.ui
import ru.dagdelo.business05.domain.global.models.CheckInfo
import javax.inject.Inject

class CheckListInteractor @Inject constructor(
    private val checkListRepositoryImpl: ru.dagdelo.business05.data.checklist.CheckListRepositoryImpl,
    private val prefs: PreferenceStorage
) {

    fun getCheckList(): Single<List<CheckInfo>> =
        checkListRepositoryImpl.getCheckList()
            .observeOn(ui)
}