package com.example.needtechnology.domain.global.repositories

import com.example.needtechnology.domain.global.models.Check
import com.example.needtechnology.domain.global.models.CheckInfoEntity
import io.reactivex.Completable
import io.reactivex.Single

interface HomeRepository {
    fun prepareCheck(check: Check): Completable

    fun insertCheck(check: CheckInfoEntity.Document.Receipt): Completable
}