package ru.dagdelo.business05.data.app

import ru.dagdelo.business05.data.global.netwotk.ApiDagDelo
import ru.dagdelo.business05.domain.global.repositories.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val apiDagDelo: ApiDagDelo
) : AppRepository {

}