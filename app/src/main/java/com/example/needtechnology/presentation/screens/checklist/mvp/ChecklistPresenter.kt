package com.example.needtechnology.presentation.screens.checklist.mvp

import com.arellomobile.mvp.InjectViewState
import com.example.needtechnology.presentation.global.base.BasePresenter
import com.example.needtechnology.presentation.global.navigation.AppRouter
import javax.inject.Inject

@InjectViewState
class ChecklistPresenter @Inject constructor(
    private val appRouter: AppRouter
): BasePresenter<ChecklistView>(appRouter) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }
}