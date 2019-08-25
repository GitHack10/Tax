package ru.dagdelo.business05.data.checklist

import io.reactivex.Single
import ru.dagdelo.business05.data.global.netwotk.ApiDagDelo
import ru.dagdelo.business05.domain.global.common.io
import ru.dagdelo.business05.domain.global.models.CheckInfo
import ru.dagdelo.business05.domain.global.repositories.CheckListRepository
import javax.inject.Inject

class CheckListRepositoryImpl @Inject constructor(
    private val apiDagDelo: ApiDagDelo
) : CheckListRepository {

    override fun getCheckList(page: Int): Single<List<CheckInfo>> =
        apiDagDelo.getCheckList(page)
            .subscribeOn(io)
}