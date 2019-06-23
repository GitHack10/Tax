package com.example.needtechnology.presentation.screens.home.mvp

import com.arellomobile.mvp.InjectViewState
import com.example.needtechnology.presentation.global.base.BasePresenter
import com.example.needtechnology.presentation.global.navigation.AppRouter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class HomePresenter @Inject constructor(
    private val appRouter: Router
): BasePresenter<HomeView>(appRouter) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }
}