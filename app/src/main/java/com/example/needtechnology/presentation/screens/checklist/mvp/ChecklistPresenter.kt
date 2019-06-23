package com.example.needtechnology.presentation.screens.checklist.mvp

import com.arellomobile.mvp.InjectViewState
import com.example.needtechnology.presentation.global.base.BasePresenter
import com.example.needtechnology.presentation.global.navigation.AppRouter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ChecklistPresenter @Inject constructor(
    private val appRouter: Router
): BasePresenter<ChecklistView>(appRouter) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }
}