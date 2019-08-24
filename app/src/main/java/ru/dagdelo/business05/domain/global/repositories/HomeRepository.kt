package ru.dagdelo.business05.domain.global.repositories

import io.reactivex.Completable
import ru.dagdelo.business05.domain.global.models.Check

interface HomeRepository {
    fun prepareCheck(check: Check): Completable
}