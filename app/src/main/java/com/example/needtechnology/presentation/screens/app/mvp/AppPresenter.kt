package com.example.needtechnology.presentation.screens.app.mvp

import com.arellomobile.mvp.InjectViewState
import com.example.needtechnology.presentation.global.Screens
import com.example.needtechnology.presentation.global.base.BasePresenter
import com.example.needtechnology.presentation.global.navigation.AppRouter
import javax.inject.Inject

@InjectViewState
class AppPresenter @Inject constructor(
    private val appRouter: AppRouter
) : BasePresenter<AppView>(appRouter) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        appRouter.replaceScreen(Screens.MainFlow())
//        login()
    }

//    private fun login() {
//        if (interactor.getPhone.isNotEmpty() && interactor.isLogin) {
//            appRouter.replaceScreen(Screens.MainFlow())
//        } else appRouter.replaceScreen(Screens.PhoneInput())
//    }

    override fun onBackPressed() = appRouter.exit()
}
