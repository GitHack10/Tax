package com.example.needtechnology.data.home

import com.example.needtechnology.data.database.AppDatabase
import com.example.needtechnology.data.global.netwotk.ApiDagDelo
import com.example.needtechnology.domain.global.common.io
import com.example.needtechnology.domain.global.models.Check
import com.example.needtechnology.domain.global.models.CheckInfoEntity
import com.example.needtechnology.domain.global.repositories.HomeRepository
import io.reactivex.Completable
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val apiDagDelo: ApiDagDelo,
    private val appDatabase: AppDatabase
): HomeRepository {

    override fun prepareCheck(check: Check): Completable =
        apiDagDelo.prepareCheck(
            fn = check.fn,
            fd = check.fd,
            fpd = check.fpd
        ).subscribeOn(io)

    override fun insertCheck(check: CheckInfoEntity.Document.Receipt): Completable = Completable.fromAction {
        appDatabase.checkDao()
            .insertCheck(check)
    }
}