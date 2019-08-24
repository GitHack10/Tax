package ru.dagdelo.business05.data.home

import io.reactivex.Completable
import ru.dagdelo.business05.data.global.netwotk.ApiDagDelo
import ru.dagdelo.business05.domain.global.common.io
import ru.dagdelo.business05.domain.global.models.Check
import ru.dagdelo.business05.domain.global.repositories.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val apiDagDelo: ApiDagDelo
): HomeRepository {

    override fun prepareCheck(check: Check): Completable =
        apiDagDelo.prepareCheck(
            fn = check.fn,
            fd = check.fd,
            fpd = check.fpd,
            date = check.date,
            type = check.type,
            sum = check.sum
        ).subscribeOn(io)
}