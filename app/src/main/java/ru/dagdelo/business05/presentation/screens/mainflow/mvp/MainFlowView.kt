package ru.dagdelo.business05.presentation.screens.mainflow.mvp

import com.arellomobile.mvp.MvpView

interface MainFlowView : MvpView {
    fun highlightTab(selectedScreen: Int)
}