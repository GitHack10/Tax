package ru.dagdelo.business05.domain.global.repositories

import io.reactivex.Completable
import ru.dagdelo.business05.domain.global.models.Check
import ru.dagdelo.business05.domain.global.models.CheckInfoEntity

interface HomeRepository {
    fun prepareCheck(check: Check): Completable

    fun insertCheck(check: CheckInfoEntity.Document.Receipt): Completable
}