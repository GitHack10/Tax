package com.example.needtechnology.data.app

import com.example.needtechnology.data.global.netwotk.ApiBusinessDag
import com.example.needtechnology.di.global.nameds.NALOG_API
import com.example.needtechnology.domain.global.repositories.AppRepository
import javax.inject.Inject
import javax.inject.Named

class AppRepositoryImpl @Inject constructor(
    @Named(NALOG_API) private val apiBusinessDag: ApiBusinessDag
): AppRepository {

}