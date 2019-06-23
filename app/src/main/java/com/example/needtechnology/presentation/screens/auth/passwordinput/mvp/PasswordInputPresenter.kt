package com.example.needtechnology.presentation.screens.auth.passwordinput.mvp

import com.arellomobile.mvp.InjectViewState
import com.example.needtechnology.presentation.global.base.BasePresenter
import com.example.needtechnology.presentation.global.navigation.AppRouter
import javax.inject.Inject

@InjectViewState
class PasswordInputPresenter @Inject constructor(
    private val appRouter: AppRouter
): BasePresenter<PasswordInputView>(appRouter){

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }
}