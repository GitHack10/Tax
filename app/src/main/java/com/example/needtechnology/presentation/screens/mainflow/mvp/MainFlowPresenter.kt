package com.example.needtechnology.presentation.screens.mainflow.mvp

import com.arellomobile.mvp.InjectViewState
import com.example.needtechnology.di.global.nameds.MAIN_FLOW
import com.example.needtechnology.presentation.global.Screens
import com.example.needtechnology.presentation.global.base.BasePresenter
import com.example.needtechnology.presentation.global.navigation.FlowRouter
import javax.inject.Inject
import javax.inject.Named

@InjectViewState
class MainFlowPresenter @Inject constructor(
    @Named(MAIN_FLOW) private val flowRouter: FlowRouter
) : BasePresenter<MainFlowView>(flowRouter) {

    private var selectedScreen = 0

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        homeTabClicked()
    }

    fun homeTabClicked() {
        if (selectedScreen != 1) {
            viewState.highlightTab(HOME_TAB_POSITION)
            flowRouter.replaceScreen(Screens.Home())
            selectedScreen = selectedScreen
        }
    }

    fun checklistTabClicked() {
        if (selectedScreen != 2) {
            viewState.highlightTab(CHECKLIST_TAB_POSITION)
            flowRouter.replaceScreen(Screens.Checklist())
            selectedScreen = selectedScreen
        }
    }

    fun newsTabClicked() {
        if (selectedScreen != 3) {
            viewState.highlightTab(NEWS_TAB_POSITION)
            flowRouter.replaceScreen(Screens.News())
            selectedScreen = selectedScreen
        }
    }

    fun profileTabClicked() {
        if (selectedScreen != 4) {
            viewState.highlightTab(PROFILE_TAB_POSITION)
            flowRouter.replaceScreen(Screens.Profile())
            selectedScreen = selectedScreen
        }
    }

    companion object {
        const val HOME_TAB_POSITION = 0
        const val CHECKLIST_TAB_POSITION = 1
        const val NEWS_TAB_POSITION = 2
        const val PROFILE_TAB_POSITION = 3
    }
}