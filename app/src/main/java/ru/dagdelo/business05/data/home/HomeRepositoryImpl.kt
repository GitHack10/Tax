package ru.dagdelo.business05.data.home

import io.reactivex.Completable
import ru.dagdelo.business05.data.global.netwotk.ApiDagDelo
import ru.dagdelo.business05.domain.global.common.io
import ru.dagdelo.business05.domain.global.models.Check
import ru.dagdelo.business05.domain.global.models.CheckInfoEntity
import ru.dagdelo.business05.domain.global.repositories.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val apiDagDelo: ApiDagDelo,
    private val appDatabase: ru.dagdelo.business05.data.database.AppDatabase
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