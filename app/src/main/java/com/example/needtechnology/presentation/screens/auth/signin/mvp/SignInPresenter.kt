package com.example.needtechnology.presentation.screens.auth.signin.mvp

import com.arellomobile.mvp.InjectViewState
import com.example.needtechnology.di.global.nameds.SIGN_IN_FLOW
import com.example.needtechnology.domain.auth.AuthInteractor
import com.example.needtechnology.domain.global.UserInfo
import com.example.needtechnology.presentation.global.Screens
import com.example.needtechnology.presentation.global.base.BasePresenter
import com.example.needtechnology.presentation.global.navigation.FlowRouter
import javax.inject.Inject
import javax.inject.Named

@InjectViewState
class SignInPresenter @Inject constructor(
    @Named(SIGN_IN_FLOW) private val flowRouter: FlowRouter,
    private val interactor: AuthInteractor,
    private var userInfo: UserInfo
) : BasePresenter<SignInView>(flowRouter) {

    fun signInClicked(email: String, password: String) {
        val userInfo = interactor.getUserInfo()
        if (userInfo.email == email && password == "111222") {
            interactor.setIsLogin(true)

            navigateToHomeScreen()
        } else {
            viewState.showErrorData(true)
        }
    }

    private fun navigateToHomeScreen() {
        flowRouter.newRootFlow(Screens.MainFlow())
    }

    override fun onBackPressed() = flowRouter.exitFlow()

    fun registrationClicked() {
        flowRouter.navigateToFlow(Screens.SignUp())
    }
}