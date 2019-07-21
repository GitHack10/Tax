package com.example.needtechnology.data.auth

import com.example.needtechnology.data.global.netwotk.ApiBusinessDag
import com.example.needtechnology.domain.global.repositories.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiBusinessDag: ApiBusinessDag
): AuthRepository {

    override fun signInRequest(authData: String) = apiBusinessDag
        .loginRequest(authData)
}