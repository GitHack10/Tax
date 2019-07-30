package com.example.needtechnology.domain.checklist

import com.example.needtechnology.data.checklist.CheckListRepositoryImpl
import com.example.needtechnology.data.global.local.PreferenceStorage
import com.example.needtechnology.domain.global.common.ui
import com.example.needtechnology.domain.global.models.CheckInfoEntity
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class CheckListInteractor @Inject constructor(
    private val checkListRepositoryImpl: CheckListRepositoryImpl,
    private val prefs: PreferenceStorage
) {

    fun getCheckList(): Single<List<CheckInfoEntity>> =
        checkListRepositoryImpl.getCheckList()
            .observeOn(ui)

   fun getAllChecks(): Flowable<List<CheckInfoEntity.Document.Receipt>> =
       checkListRepositoryImpl.getAllChecks()
           .observeOn(ui)
}