package ru.dagdelo.business05.data.checklist

import io.reactivex.Flowable
import io.reactivex.Single
import ru.dagdelo.business05.data.global.netwotk.ApiDagDelo
import ru.dagdelo.business05.domain.global.common.io
import ru.dagdelo.business05.domain.global.models.CheckInfo
import ru.dagdelo.business05.domain.global.models.CheckInfoEntity
import ru.dagdelo.business05.domain.global.repositories.CheckListRepository
import javax.inject.Inject

class CheckListRepositoryImpl @Inject constructor(
    private val apiDagDelo: ApiDagDelo,
    private val appDatabase: ru.dagdelo.business05.data.database.AppDatabase
) : CheckListRepository {

    override fun getCheckList(): Single<List<CheckInfo>> =
        apiDagDelo.getCheckList()
            .subscribeOn(io)


    override fun getAllChecks(): Flowable<List<CheckInfoEntity.Document.Receipt>> =
        appDatabase.checkDao().getAllChecks()
            .subscribeOn(io)
}