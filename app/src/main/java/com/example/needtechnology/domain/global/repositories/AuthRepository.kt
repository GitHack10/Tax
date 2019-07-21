package com.example.needtechnology.domain.global.repositories

import com.example.needtechnology.domain.global.LoginResponse
import io.reactivex.Single

interface AuthRepository {

    fun signInRequest(authData: String): Single<LoginResponse>
}