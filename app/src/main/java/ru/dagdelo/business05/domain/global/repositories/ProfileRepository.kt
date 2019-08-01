package ru.dagdelo.business05.domain.global.repositories

import io.reactivex.Completable
import io.reactivex.Single
import ru.dagdelo.business05.domain.global.models.User

interface ProfileRepository {
    fun getUserInfo(): Single<User>
    fun editProfile(username: String): Completable
}