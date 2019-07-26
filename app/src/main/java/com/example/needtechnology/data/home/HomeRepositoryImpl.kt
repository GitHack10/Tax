package com.example.needtechnology.data.home

import com.example.needtechnology.data.global.netwotk.ApiBusinessDag
import com.example.needtechnology.domain.global.repositories.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val apiBusinessDag: ApiBusinessDag
): HomeRepository {

}