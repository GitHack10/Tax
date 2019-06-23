package com.example.needtechnology.presentation.screens.profile.mvp

import com.arellomobile.mvp.InjectViewState
import com.example.needtechnology.presentation.global.base.BasePresenter
import com.example.needtechnology.presentation.global.navigation.AppRouter
import javax.inject.Inject

@InjectViewState
class ProfilePresenter @Inject constructor(
    private val appRouter: AppRouter
): BasePresenter<ProfileView>(appRouter) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }
}