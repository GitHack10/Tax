package com.example.needtechnology.presentation.screens.app.mvp

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.needtechnology.domain.app.AppInteractor
import com.example.needtechnology.presentation.global.Screens
import com.example.needtechnology.presentation.global.base.BasePresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class AppPresenter @Inject constructor(
    private val appRouter: Router,
    private val interactor: AppInteractor
) : BasePresenter<AppView>(appRouter) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        login()
    }

    private fun login() {
        if (interactor.isLogin()) {
            appRouter.replaceScreen(Screens.MainFlow())
        } else appRouter.replaceScreen(Screens.EnterPhone())
    }

    override fun onBackPressed() = appRouter.exit()
    fun saveDeviceId(deviceId: String) {
        interactor.saveDeviceId(deviceId)
        Log.d("DEVICE-ID: ", deviceId)
    }
}
