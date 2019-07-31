package com.example.needtechnology.data.profile

import com.example.needtechnology.data.global.netwotk.ApiDagDelo
import com.example.needtechnology.domain.global.common.io
import com.example.needtechnology.domain.global.models.User
import com.example.needtechnology.domain.global.repositories.ProfileRepository
import io.reactivex.Single
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val apiDagDelo: ApiDagDelo
) : ProfileRepository {

    override fun getUserInfo(): Single<User> =
        apiDagDelo.getUserInfo()
            .subscribeOn(io)
}