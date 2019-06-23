package com.example.needtechnology.presentation.screens.auth.passwordinput.mvp

import com.arellomobile.mvp.InjectViewState
import com.example.needtechnology.di.global.nameds.PASSWORD_INPUT_FLOW
import com.example.needtechnology.presentation.global.base.BasePresenter
import com.example.needtechnology.presentation.global.navigation.FlowRouter
import javax.inject.Inject
import javax.inject.Named

@InjectViewState
class PasswordInputPresenter @Inject constructor(
    @Named(PASSWORD_INPUT_FLOW) private val flowRouter: FlowRouter
): BasePresenter<PasswordInputView>(flowRouter){

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    override fun onBackPressed() = flowRouter.exitFlow()
}