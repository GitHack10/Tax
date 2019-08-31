package ru.dagdelo.business05.domain.global.repositories

import io.reactivex.Single
import ru.dagdelo.business05.domain.global.models.CheckInfo

interface CheckListRepository {
    fun getCheckList(page: Int, selectedFilter: Int?): Single<List<CheckInfo>>
}