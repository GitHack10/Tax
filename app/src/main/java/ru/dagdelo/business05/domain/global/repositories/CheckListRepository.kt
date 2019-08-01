package ru.dagdelo.business05.domain.global.repositories

import io.reactivex.Flowable
import io.reactivex.Single
import ru.dagdelo.business05.domain.global.models.CheckInfo
import ru.dagdelo.business05.domain.global.models.CheckInfoEntity

interface CheckListRepository {
    fun getCheckList(): Single<List<CheckInfo>>
    fun getAllChecks(): Flowable<List<CheckInfoEntity.Document.Receipt>>
}