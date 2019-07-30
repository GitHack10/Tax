package com.example.needtechnology.domain.global.repositories

import com.example.needtechnology.domain.global.models.CheckInfoEntity
import io.reactivex.Flowable
import io.reactivex.Single

interface CheckListRepository {
    fun getCheckList(): Single<List<CheckInfoEntity>>
    fun getAllChecks(): Flowable<List<CheckInfoEntity.Document.Receipt>>
}