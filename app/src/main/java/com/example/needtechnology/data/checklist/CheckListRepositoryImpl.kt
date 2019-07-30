package com.example.needtechnology.data.checklist

import com.example.needtechnology.data.database.AppDatabase
import com.example.needtechnology.data.global.netwotk.ApiDagDelo
import com.example.needtechnology.di.global.nameds.DAGDELO_API
import com.example.needtechnology.domain.global.common.io
import com.example.needtechnology.domain.global.models.CheckInfoEntity
import com.example.needtechnology.domain.global.repositories.CheckListRepository
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class CheckListRepositoryImpl @Inject constructor(
    @Named(DAGDELO_API) private val apiDagDelo: ApiDagDelo,
    private val appDatabase: AppDatabase
): CheckListRepository {

    override fun getCheckList(): Single<List<CheckInfoEntity>> =
        apiDagDelo.getCheckList()
            .subscribeOn(io)


    override fun getAllChecks(): Flowable<List<CheckInfoEntity.Document.Receipt>> =
        appDatabase.checkDao().getAllChecks()
            .subscribeOn(io)
}