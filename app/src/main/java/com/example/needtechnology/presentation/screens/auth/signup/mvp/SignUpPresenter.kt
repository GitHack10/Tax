package com.example.needtechnology.presentation.screens.auth.signup.mvp

import com.example.needtechnology.di.global.nameds.SIGN_UP_FLOW
import com.example.needtechnology.domain.auth.AuthInteractor
import com.example.needtechnology.domain.global.UserInfo
import com.example.needtechnology.presentation.global.Screens
import com.example.needtechnology.presentation.global.base.BasePresenter
import com.example.needtechnology.presentation.global.navigation.FlowRouter
import javax.inject.Inject
import javax.inject.Named

class SignUpPresenter@Inject constructor(
    @Named(SIGN_UP_FLOW) private val flowRouter: FlowRouter,
    private val interactor: AuthInteractor,
    private var userInfo: UserInfo
): BasePresenter<SignUpView>(flowRouter) {

    fun onDoneClicked(userInfo: UserInfo) {
        interactor.saveUserInfo(userInfo)
        interactor.setIsLogin(true)
        flowRouter.newRootFlow(Screens.MainFlow())
    }

    override fun onBackPressed() = flowRouter.exitFlow()
}