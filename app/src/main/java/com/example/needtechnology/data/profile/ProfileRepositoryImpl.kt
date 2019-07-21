package com.example.needtechnology.data.profile

import com.example.needtechnology.data.global.netwotk.ApiBusinessDag
import com.example.needtechnology.domain.global.repositories.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val apiBusinessDag: ApiBusinessDag
): ProfileRepository {
}