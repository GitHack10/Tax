package com.example.needtechnology.domain.global.repositories

import com.example.needtechnology.domain.global.models.User
import io.reactivex.Single

interface ProfileRepository {

    fun getUserInfo(): Single<User>
}