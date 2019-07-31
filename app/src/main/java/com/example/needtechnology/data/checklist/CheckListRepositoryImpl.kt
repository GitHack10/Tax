package com.example.needtechnology.data.checklist

import com.example.needtechnology.data.database.AppDatabase
import com.example.needtechnology.data.global.netwotk.ApiDagDelo
import com.example.needtechnology.domain.global.common.io
import com.example.needtechnology.domain.global.models.CheckInfo
import com.example.needtechnology.domain.global.models.CheckInfoEntity
import com.example.needtechnology.domain.global.repositories.CheckListRepository
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class CheckListRepositoryImpl @Inject constructor(
    private val apiDagDelo: ApiDagDelo,
    private val appDatabase: AppDatabase
) : CheckListRepository {

    override fun getCheckList(): Single<List<CheckInfo>> =
        apiDagDelo.getCheckList()
            .subscribeOn(io)


    override fun getAllChecks(): Flowable<List<CheckInfoEntity.Document.Receipt>> =
        appDatabase.checkDao().getAllChecks()
            .subscribeOn(io)
}