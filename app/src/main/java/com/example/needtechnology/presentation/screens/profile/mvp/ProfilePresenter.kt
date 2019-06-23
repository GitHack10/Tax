package com.example.needtechnology.presentation.screens.profile.mvp

import com.arellomobile.mvp.InjectViewState
import com.example.needtechnology.presentation.global.base.BasePresenter
import com.example.needtechnology.presentation.global.navigation.AppRouter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ProfilePresenter @Inject constructor(
    private val appRouter: Router
): BasePresenter<ProfileView>(appRouter) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }
}