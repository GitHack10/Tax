package com.example.needtechnology.data.home

import com.example.needtechnology.data.database.AppDatabase
import com.example.needtechnology.data.global.netwotk.ApiBusinessDag
import com.example.needtechnology.data.global.netwotk.ApiDagDelo
import com.example.needtechnology.data.global.netwotk.utils.createBasicAuthHeader
import com.example.needtechnology.di.global.nameds.DAGDELO_API
import com.example.needtechnology.domain.global.common.io
import com.example.needtechnology.domain.global.models.Check
import com.example.needtechnology.domain.global.models.CheckInfoEntity
import com.example.needtechnology.domain.global.repositories.HomeRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class HomeRepositoryImpl @Inject constructor(
    @Named(DAGDELO_API) private val apiDagDelo: ApiDagDelo,
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