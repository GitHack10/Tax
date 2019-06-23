package com.example.needtechnology.presentation.screens.auth.phoneinput.mvp

import com.arellomobile.mvp.InjectViewState
import com.example.needtechnology.presentation.global.base.BasePresenter
import com.example.needtechnology.presentation.global.navigation.AppRouter
import javax.inject.Inject

@InjectViewState
class PhoneInputPresenter @Inject constructor(
    private val appRouter: AppRouter
): BasePresenter<PhoneInputView>(appRouter) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }
}