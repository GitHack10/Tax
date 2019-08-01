package ru.dagdelo.business05.data.profile

import io.reactivex.Completable
import io.reactivex.Single
import ru.dagdelo.business05.data.global.netwotk.ApiDagDelo
import ru.dagdelo.business05.domain.global.common.io
import ru.dagdelo.business05.domain.global.models.EditProfile
import ru.dagdelo.business05.domain.global.models.User
import ru.dagdelo.business05.domain.global.repositories.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val apiDagDelo: ApiDagDelo
) : ProfileRepository {

    override fun getUserInfo(): Single<User> =
        apiDagDelo.getUserInfo()
            .subscribeOn(io)

    override fun editProfile(username: String): Completable =
        apiDagDelo.editProfile(EditProfile(username))
            .subscribeOn(io)
}