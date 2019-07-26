package com.example.needtechnology.domain.home

import com.example.needtechnology.data.global.local.PreferenceStorage
import com.example.needtechnology.data.home.HomeRepositoryImpl
import javax.inject.Inject

class HomeInteractor @Inject constructor(
    private val homeRepositoryImpl: HomeRepositoryImpl,
    private val prefs: PreferenceStorage
) {

}