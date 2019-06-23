package com.example.needtechnology.presentation.screens.home

import com.arellomobile.mvp.InjectViewState
import com.example.needtechnology.presentation.global.base.BasePresenter
import com.example.needtechnology.presentation.global.navigation.AppRouter
import javax.inject.Inject

@InjectViewState
class HomePresenter @Inject constructor(
    private val appRouter: AppRouter
): BasePresenter<HomeView>(appRouter) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }
}