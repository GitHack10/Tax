package com.example.needtechnology.presentation.screens.auth.phoneinput.mvp

import com.arellomobile.mvp.InjectViewState
import com.example.needtechnology.di.global.nameds.PHONE_INPUT_FLOW
import com.example.needtechnology.presentation.global.base.BasePresenter
import com.example.needtechnology.presentation.global.navigation.FlowRouter
import javax.inject.Inject
import javax.inject.Named

@InjectViewState
class PhoneInputPresenter @Inject constructor(
    @Named(PHONE_INPUT_FLOW) private val flowRouter: FlowRouter
): BasePresenter<PhoneInputView>(flowRouter) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    override fun onBackPressed() = flowRouter.exitFlow()
}