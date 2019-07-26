package com.example.needtechnology.presentation.screens.home.mvp

import com.arellomobile.mvp.InjectViewState
import com.example.needtechnology.domain.home.HomeInteractor
import com.example.needtechnology.presentation.global.Screens
import com.example.needtechnology.presentation.global.base.BasePresenter
import com.example.needtechnology.presentation.global.navigation.AppRouter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class HomePresenter @Inject constructor(
    private val appRouter: Router,
    private val interactor: HomeInteractor
): BasePresenter<HomeView>(appRouter) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    fun onQrScannerClicked() {
        appRouter.navigateTo(Screens.QrScreen())
    }

    fun getScannedString() {

    }
}