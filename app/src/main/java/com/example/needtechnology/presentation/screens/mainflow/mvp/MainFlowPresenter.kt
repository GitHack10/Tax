package com.example.needtechnology.presentation.screens.mainflow.mvp

import com.arellomobile.mvp.InjectViewState
import com.example.needtechnology.presentation.global.Screens
import com.example.needtechnology.presentation.global.base.BasePresenter
import com.example.needtechnology.presentation.global.navigation.AppRouter
import javax.inject.Inject

@InjectViewState
class MainFlowPresenter @Inject constructor(
    private val appRouter: AppRouter
) : BasePresenter<MainFlowView>(appRouter) {

    private var selectedScreen = 0

    fun homeTabClicked(selectedScreen: Int) {
        if (this.selectedScreen != selectedScreen) {
            viewState.highlightTab(selectedScreen)
            appRouter.replaceScreen(Screens.Home())
            this.selectedScreen = selectedScreen
        }
    }

    fun checklistTabClicked(selectedScreen: Int) {
        if (this.selectedScreen != selectedScreen) {
            viewState.highlightTab(selectedScreen)
            appRouter.replaceScreen(Screens.Checklist())
            this.selectedScreen = selectedScreen
        }
    }

    fun newsTabClicked(selectedScreen: Int) {
        if (this.selectedScreen != selectedScreen) {
            viewState.highlightTab(selectedScreen)
            appRouter.replaceScreen(Screens.News())
            this.selectedScreen = selectedScreen
        }
    }

    fun profileTabClicked(selectedScreen: Int) {
        if (this.selectedScreen != selectedScreen) {
            viewState.highlightTab(selectedScreen)
            appRouter.replaceScreen(Screens.Profile())
            this.selectedScreen = selectedScreen
        }
    }
}