package ru.dagdelo.business05.presentation.screens.mainflow.mvp

import com.arellomobile.mvp.InjectViewState
import ru.dagdelo.business05.di.global.nameds.MAIN_FLOW
import ru.dagdelo.business05.presentation.global.Screens
import ru.dagdelo.business05.presentation.global.base.BasePresenter
import ru.dagdelo.business05.presentation.global.navigation.FlowRouter
import javax.inject.Inject
import javax.inject.Named

@InjectViewState
class MainFlowPresenter @Inject constructor(
    @Named(MAIN_FLOW) private val flowRouter: FlowRouter
) : BasePresenter<MainFlowView>(flowRouter) {

    private var selectedScreen = 0

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        homeTabClicked(selectedScreen)
    }

    fun homeTabClicked(selectedScreen: Int) {
        if (selectedScreen != 1) {
            viewState.highlightTab(HOME_TAB_POSITION)
            flowRouter.replaceScreen(Screens.Home)
            this.selectedScreen = selectedScreen
        }
    }

    fun checklistTabClicked(selectedScreen: Int) {
        if (selectedScreen != 2) {
            viewState.highlightTab(CHECKLIST_TAB_POSITION)
            flowRouter.replaceScreen(Screens.Checklist())
            this.selectedScreen = selectedScreen
        }
    }

    fun newsTabClicked(selectedScreen: Int) {
        if (selectedScreen != 3) {
            viewState.highlightTab(NEWS_TAB_POSITION)
            flowRouter.replaceScreen(Screens.News())
            this.selectedScreen = selectedScreen
        }
    }

    fun profileTabClicked(selectedScreen: Int) {
        if (selectedScreen != 4) {
            viewState.highlightTab(PROFILE_TAB_POSITION)
            flowRouter.replaceScreen(Screens.Profile())
            this.selectedScreen = selectedScreen
        }
    }

    companion object {
        const val HOME_TAB_POSITION = 0
        const val CHECKLIST_TAB_POSITION = 1
        const val NEWS_TAB_POSITION = 2
        const val PROFILE_TAB_POSITION = 3
    }
}