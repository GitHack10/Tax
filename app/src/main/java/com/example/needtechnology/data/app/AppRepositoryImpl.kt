package com.example.needtechnology.data.app

import com.example.needtechnology.data.global.netwotk.ApiDagDelo
import com.example.needtechnology.domain.global.repositories.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val apiDagDelo: ApiDagDelo
) : AppRepository {

}